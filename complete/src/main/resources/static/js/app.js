/* ═══════════════════════════════════════════════════════════════════════
   app.js — JavaScript dùng chung cho toàn bộ Smart Warehouse
   Đặt vào: src/main/resources/static/js/app.js

   Nội dung:
   1. Clock realtime
   2. Scroll Reveal (IntersectionObserver) — kiến trúc từ Zoho
   3. Toast notification
   4. Delete modal
   5. Stock bar animation
   ═══════════════════════════════════════════════════════════════════════ */


/* ═══════════════════════════════════════════════════════════════════════
   1. CLOCK — cập nhật mỗi giây
   ═══════════════════════════════════════════════════════════════════════ */
function updateClock() {
  const el = document.getElementById('clock');
  if (el) {
    el.textContent = new Date().toLocaleString('vi-VN');
  }
}
updateClock();
setInterval(updateClock, 1000);


/* ═══════════════════════════════════════════════════════════════════════
   2. SCROLL REVEAL — kiến trúc từ Zoho
      Nguyên tắc quan trọng: JavaScript KHÔNG set style trực tiếp.
      JS chỉ làm một việc: thêm class "reveal-active" khi element
      vào viewport. Toàn bộ animation (opacity, transform, timing)
      đều định nghĩa trong CSS — browser tự tối ưu hardware acceleration.

      Tại sao tốt hơn GSAP/anime.js?
      → Không cần thư viện ngoài, nhẹ hơn, browser có thể batch
        composite operations cùng lúc, không jank khi scroll nhanh.
   ═══════════════════════════════════════════════════════════════════════ */
(function initScrollReveal() {
  /* Nếu browser không hỗ trợ IntersectionObserver (IE11), fallback:
     hiện tất cả luôn không animation */
  if (!window.IntersectionObserver) {
    document.querySelectorAll('.reveal, .reveal-scale, .reveal-left, .reveal-right')
      .forEach(el => el.classList.add('reveal-active'));
    return;
  }

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('reveal-active');
          /* Sau khi animation chạy xong, unobserve để không trigger lại
             (khác Zoho — họ để trigger lại, nhưng unobserve nhẹ hơn) */
          observer.unobserve(entry.target);
        }
      });
    },
    {
      /* threshold: 0.1 = element phải vào viewport 10% mới trigger
         rootMargin: -40px = trigger sớm hơn 40px so với viewport edge
         → animation bắt đầu khi người dùng vừa thấy element, không phải
           sau khi đã thấy một nửa rồi */
      threshold: 0.08,
      rootMargin: '0px 0px -40px 0px'
    }
  );

  /* Quan sát tất cả element có class reveal* */
  document.querySelectorAll('.reveal, .reveal-scale, .reveal-left, .reveal-right')
    .forEach(el => observer.observe(el));
})();


/* ═══════════════════════════════════════════════════════════════════════
   3. COUNTER ANIMATION — dùng cho stat cards dashboard
      Đếm từ 0 lên số target khi card vào viewport
      data-target="248" — set số đích trong HTML
   ═══════════════════════════════════════════════════════════════════════ */
(function initCounters() {
  const counters = document.querySelectorAll('[data-target]');
  if (!counters.length) return;

  const counterObserver = new IntersectionObserver(
    (entries) => {
      entries.forEach(entry => {
        if (!entry.isIntersecting) return;

        const el     = entry.target;
        const target = parseInt(el.dataset.target, 10);
        const duration = 900; /* ms */
        const startTime = performance.now();

        function tick(currentTime) {
          const elapsed  = currentTime - startTime;
          const progress = Math.min(elapsed / duration, 1);
          /* easeOutCubic — nhanh lúc đầu, chậm lại cuối */
          const eased    = 1 - Math.pow(1 - progress, 3);
          el.textContent = Math.round(eased * target).toLocaleString('vi-VN');

          if (progress < 1) {
            requestAnimationFrame(tick);
          } else {
            el.textContent = target.toLocaleString('vi-VN');
          }
        }

        requestAnimationFrame(tick);
        counterObserver.unobserve(el);
      });
    },
    { threshold: 0.5 }
  );

  counters.forEach(el => counterObserver.observe(el));
})();


/* ═══════════════════════════════════════════════════════════════════════
   4. STOCK BAR ANIMATION
      Khi stock bar vào viewport, width animate từ 0 lên data-width
      Kết hợp với CSS transition: width 0.8s ease (trong style.css)
   ═══════════════════════════════════════════════════════════════════════ */
(function initStockBars() {
  const bars = document.querySelectorAll('.stock-fill[data-width]');
  if (!bars.length) return;

  /* Đặt width = 0 ban đầu, CSS transition sẽ animate lên */
  bars.forEach(bar => { bar.style.width = '0%'; });

  const barObserver = new IntersectionObserver(
    (entries) => {
      entries.forEach(entry => {
        if (!entry.isIntersecting) return;
        /* Delay nhỏ để đảm bảo CSS transition nhận được thay đổi */
        setTimeout(() => {
          entry.target.style.width = entry.target.dataset.width + '%';
        }, 80);
        barObserver.unobserve(entry.target);
      });
    },
    { threshold: 0.3 }
  );

  bars.forEach(bar => barObserver.observe(bar));
})();


/* ═══════════════════════════════════════════════════════════════════════
   5. TOAST NOTIFICATION
      Gọi: showToast('✅ Thành công!')
      Tự động biến mất sau 3.2s
   ═══════════════════════════════════════════════════════════════════════ */
let _toastTimer = null;

function showToast(msg) {
  const el = document.getElementById('toast');
  if (!el) return;

  el.textContent = msg;
  el.classList.add('show');

  clearTimeout(_toastTimer);
  _toastTimer = setTimeout(() => el.classList.remove('show'), 3200);
}

/* Kiểm tra nếu Spring Boot truyền flash message qua th:attr
   Ví dụ trong Controller: redirectAttributes.addFlashAttribute("toast", "✅ Đã lưu!")
   Trong layout.html thêm: <div id="flash-msg" th:data-msg="${toast}"></div>
   → JS đọc và hiển thị tự động khi trang load */
window.addEventListener('DOMContentLoaded', () => {
  const flashEl = document.getElementById('flash-msg');
  if (flashEl && flashEl.dataset.msg) {
    setTimeout(() => showToast(flashEl.dataset.msg), 300);
  }
});


/* ═══════════════════════════════════════════════════════════════════════
   6. DELETE MODAL
      Gọi: openDeleteModal('Laptop Dell XPS 15', '/hanghoa/delete/1')
   ═══════════════════════════════════════════════════════════════════════ */
function openDeleteModal(itemName, deleteUrl) {
  const modal   = document.getElementById('delete-modal');
  const nameEl  = document.getElementById('delete-modal-name');
  const confirm = document.getElementById('delete-modal-confirm');

  if (!modal) return;

  nameEl.textContent = itemName;
  /* Set href của nút "Xóa ngay" trỏ đến URL xóa thực tế */
  confirm.href = deleteUrl;

  modal.classList.add('open');
}

function closeDeleteModal() {
  const modal = document.getElementById('delete-modal');
  if (modal) modal.classList.remove('open');
}

/* Đóng modal khi click vào overlay bên ngoài */
document.addEventListener('click', (e) => {
  const modal = document.getElementById('delete-modal');
  if (modal && e.target === modal) closeDeleteModal();
});

/* Đóng khi nhấn Escape */
document.addEventListener('keydown', (e) => {
  if (e.key === 'Escape') closeDeleteModal();
});


/* ═══════════════════════════════════════════════════════════════════════
   7. CAROUSEL — Tự động rotate feature cards theo tuần hoàn
      Dùng: <section class="zoho-feature-cards" data-carousel data-interval="4000">
      JS sẽ tự động rotate các article trong section
      Opacity smooth transition, scale transform cho effect nhẹ
   ═══════════════════════════════════════════════════════════════════════ */
(function initCarousel() {
  const carousel = document.querySelector('[data-carousel]');
  if (!carousel) return;

  const articles = carousel.querySelectorAll('article');
  if (articles.length < 2) return;

  let current = 0;
  const interval = parseInt(carousel.dataset.interval || '4000', 10);

  function rotate() {
    articles.forEach((article, idx) => {
      if (idx === current) {
        article.style.opacity = '1';
        article.style.transform = 'scale(1)';
        article.style.pointerEvents = 'auto';
      } else {
        article.style.opacity = '0.5';
        article.style.transform = 'scale(0.98)';
        article.style.pointerEvents = 'none';
      }
    });
    current = (current + 1) % articles.length;
  }

  /* Rotateemé setup transitions smooth */
  articles.forEach(article => {
    article.style.transition = 'opacity 0.5s ease-in-out, transform 0.5s ease-in-out';
  });

  rotate();
  setInterval(rotate, interval);
})();