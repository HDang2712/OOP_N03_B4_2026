/**
 * app.js — JavaScript dùng chung cho toàn bộ hệ thống Smart Warehouse
 * Đặt file này vào: src/main/resources/static/js/app.js
 *
 * Mỗi trang Thymeleaf đều load file này qua:
 *   <script th:src="@{/js/app.js}" defer></script>
 */

/* ═══════════════════════════════════════════════════
   1. CLOCK — Đồng hồ realtime hiển thị trên topbar
   ═══════════════════════════════════════════════════ */
function startClock() {
  const el = document.getElementById('clock');
  if (!el) return;
  function tick() {
    el.textContent = new Date().toLocaleString('vi-VN');
  }
  tick();
  setInterval(tick, 1000);
}

/* ═══════════════════════════════════════════════════
   2. TOAST — Thông báo nhỏ xuất hiện ở góc màn hình
   Dùng: showToast('✅ Lưu thành công!');
   ═══════════════════════════════════════════════════ */
let _toastTimer = null;
function showToast(msg, duration = 3500) {
  const el = document.getElementById('toast');
  if (!el) return;
  el.innerHTML = msg;
  el.classList.add('show');
  clearTimeout(_toastTimer);
  _toastTimer = setTimeout(() => el.classList.remove('show'), duration);
}

/* ═══════════════════════════════════════════════════
   3. FLASH MESSAGE — Đọc flash message từ Thymeleaf
   RedirectAttributes.addFlashAttribute("success", "...")
   sẽ render ra div#flash-data, script này tự hiện toast.
   ═══════════════════════════════════════════════════ */
function handleFlashMessage() {
  const el = document.getElementById('flash-data');
  if (!el) return;
  const msg = el.dataset.msg;
  const type = el.dataset.type;
  if (msg) {
    const icon = type === 'success' ? '✅' : '❌';
    showToast(`${icon} ${msg}`);
  }
}

/* ═══════════════════════════════════════════════════
   4. DELETE MODAL — Hộp thoại xác nhận xóa dùng chung
   Gọi: confirmDelete('Tên item', '/hanghoa/delete/HH001')
   ═══════════════════════════════════════════════════ */
function confirmDelete(name, url) {
  const modal   = document.getElementById('delete-modal');
  const nameEl  = document.getElementById('del-modal-name');
  const linkEl  = document.getElementById('del-modal-link');
  if (!modal) return;
  nameEl.textContent = name;
  linkEl.href = url;
  modal.classList.add('open');
}
function closeDeleteModal() {
  const modal = document.getElementById('delete-modal');
  if (modal) modal.classList.remove('open');
}

/* ═══════════════════════════════════════════════════
   5. TABLE SEARCH — Tìm kiếm realtime trong bảng
   Gọi: filterTable('search-inp', 'data-table')
   ═══════════════════════════════════════════════════ */
function filterTable(inputId, tableBodyId) {
  const inp   = document.getElementById(inputId);
  const tbody = document.getElementById(tableBodyId);
  if (!inp || !tbody) return;

  inp.addEventListener('input', function () {
    const q = this.value.toLowerCase();
    const rows = tbody.querySelectorAll('tr');
    let visible = 0;
    rows.forEach(row => {
      const match = row.textContent.toLowerCase().includes(q);
      row.style.display = match ? '' : 'none';
      if (match) visible++;
    });
    /* Cập nhật bộ đếm nếu có */
    const counter = document.getElementById('row-count');
    if (counter) counter.textContent = visible;
  });
}

/* ═══════════════════════════════════════════════════
   6. COUNTER ANIMATION — Số đếm tăng dần khi load
   Áp dụng cho các .stat-num có attribute data-target
   ═══════════════════════════════════════════════════ */
function animateCounters() {
  document.querySelectorAll('[data-target]').forEach(el => {
    const target = parseInt(el.getAttribute('data-target'), 10) || 0;
    if (target === 0) { el.textContent = '0'; return; }
    let cur = 0;
    const step = Math.max(1, Math.ceil(target / 55));
    const timer = setInterval(() => {
      cur = Math.min(cur + step, target);
      el.textContent = cur.toLocaleString('vi-VN');
      if (cur >= target) clearInterval(timer);
    }, 16);
  });
}

/* ═══════════════════════════════════════════════════
   7. FORM VALIDATION — Validate form trước khi submit
   Thêm class .required vào input/select cần validate.
   ═══════════════════════════════════════════════════ */
function validateForm(formId) {
  const form = document.getElementById(formId);
  if (!form) return true;
  let valid = true;
  form.querySelectorAll('.required').forEach(el => {
    const val = el.value.trim();
    if (!val) {
      el.style.borderColor = 'var(--red)';
      el.style.boxShadow   = '0 0 0 3px rgba(239,68,68,.15)';
      valid = false;
      /* Reset sau 3 giây */
      setTimeout(() => {
        el.style.borderColor = '';
        el.style.boxShadow   = '';
      }, 3000);
    }
  });
  if (!valid) showToast('❌ Vui lòng điền đầy đủ các trường bắt buộc!');
  return valid;
}

/* ═══════════════════════════════════════════════════
   8. STATUS BADGE COLORS — Màu badge theo trạng thái
   Dùng để tô màu động nếu cần thiết từ JS.
   ═══════════════════════════════════════════════════ */
const STATUS_CLASS = {
  'DRAFT':    'badge-draft',
  'PENDING':  'badge-pending',
  'APPROVED': 'badge-approved',
  'DONE':     'badge-done',
  'REJECTED': 'badge-rejected',
};
const STATUS_LABEL = {
  'DRAFT':    '📝 DRAFT',
  'PENDING':  '⏳ PENDING',
  'APPROVED': '✔ APPROVED',
  'DONE':     '✅ DONE',
  'REJECTED': '❌ REJECTED',
};

/* ═══════════════════════════════════════════════════
   9. SIDEBAR ACTIVE — Tự highlight menu đang active
   dùng khi không muốn dùng th:classappend trong Thymeleaf.
   (Thymeleaf đã xử lý rồi, đây là fallback nếu cần)
   ═══════════════════════════════════════════════════ */
function highlightSidebar() {
  const path = window.location.pathname;
  document.querySelectorAll('.sb-link').forEach(link => {
    const href = link.getAttribute('href') || '';
    if (href && path.startsWith(href) && href !== '/') {
      link.classList.add('active');
    }
  });
}

/* ═══════════════════════════════════════════════════
   10. INIT — Chạy khi DOM đã sẵn sàng
   ═══════════════════════════════════════════════════ */
document.addEventListener('DOMContentLoaded', function () {
  startClock();
  handleFlashMessage();
  animateCounters();
  filterTable('search-inp', 'table-body');

  /* Đóng modal khi click vào overlay */
  document.querySelectorAll('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', function (e) {
      if (e.target === this) this.classList.remove('open');
    });
  });

  /* Phím ESC đóng modal */
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
      document.querySelectorAll('.modal-overlay.open').forEach(m => m.classList.remove('open'));
    }
  });
});