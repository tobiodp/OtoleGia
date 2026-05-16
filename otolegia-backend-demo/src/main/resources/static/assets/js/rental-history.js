const rentalHistoryGrid = document.getElementById("rentalHistoryGrid");
const customerId = getQueryParam("customerId") || DEMO_CUSTOMER_ID;

async function loadRentalHistory() {
  try {
    rentalHistoryGrid.innerHTML = `<div class="bg-surface-container-lowest rounded-2xl p-10 text-center text-on-surface-variant font-semibold shadow-sm">Đang tải lịch sử đơn thuê...</div>`;
    const orders = await apiGet(`/rental-orders/customer/${customerId}`);
    if (!orders.length) {
      rentalHistoryGrid.innerHTML = `<div class="bg-surface-container-lowest rounded-2xl p-10 text-center text-on-surface-variant font-semibold shadow-sm">Chưa có đơn thuê nào.</div>`;
      return;
    }
    rentalHistoryGrid.innerHTML = orders.map(renderOrderCard).join("");
  } catch (error) {
    rentalHistoryGrid.innerHTML = `<div class="bg-error-container rounded-2xl p-10 text-center text-error font-semibold">${error.message}</div>`;
  }
}

function renderOrderCard(order) {
  const status = getOrderStatus(order.trangThai);
  return `
    <div class="group bg-surface-container-lowest rounded-2xl overflow-hidden shadow-[0_12px_40px_rgba(0,31,63,0.04)] hover:shadow-[0_20px_50px_rgba(0,31,63,0.08)] transition-all duration-500 flex flex-col lg:flex-row">
      <div class="lg:w-1/3 relative overflow-hidden">
        <img alt="${order.tenXe}" class="w-full h-full min-h-[260px] object-cover transition-transform duration-700 group-hover:scale-105" src="/images/default-car.jpg"/>
        <div class="absolute top-4 left-4">
          <span class="${status.className} px-4 py-1.5 rounded-full text-xs font-bold uppercase tracking-widest flex items-center gap-2">
            <span class="w-2 h-2 rounded-full bg-current animate-pulse"></span>${status.label}
          </span>
        </div>
      </div>
      <div class="p-8 flex-1 flex flex-col justify-between">
        <div>
          <div class="flex justify-between items-start mb-4">
            <div>
              <p class="text-[10px] font-black uppercase tracking-widest text-on-primary-container mb-1">Mã đơn: ${order.maDonThue}</p>
              <h3 class="text-2xl font-extrabold text-primary">${order.tenXe}</h3>
              <p class="text-sm text-on-surface-variant mt-1">${order.hangXe} • ${order.tenKhachHang}</p>
            </div>
            <div class="text-right">
              <p class="text-[10px] font-bold uppercase text-slate-400">${order.trangThaiThanhToan === "SUCCESS" ? "Đã thanh toán" : "Chưa thanh toán"}</p>
              <p class="text-xl font-black text-primary">${formatMoney(order.tongTien)}</p>
            </div>
          </div>
          <div class="grid grid-cols-2 gap-8 mb-8">
            <div class="flex items-center gap-4">
              <div class="w-12 h-12 rounded-xl bg-surface-container flex items-center justify-center"><span class="material-symbols-outlined text-primary">nest_clock_farsight_analog</span></div>
              <div><p class="text-[10px] font-bold text-slate-400 uppercase">Ngày nhận</p><p class="text-sm font-bold text-primary">${formatDateTime(order.thoiGianNhan)}</p></div>
            </div>
            <div class="flex items-center gap-4">
              <div class="w-12 h-12 rounded-xl bg-surface-container flex items-center justify-center"><span class="material-symbols-outlined text-primary">event_available</span></div>
              <div><p class="text-[10px] font-bold text-slate-400 uppercase">Ngày trả</p><p class="text-sm font-bold text-primary">${formatDateTime(order.thoiGianTra)}</p></div>
            </div>
          </div>
        </div>
        <div class="flex gap-4 border-t border-surface-container pt-6">
          <button onclick="viewOrder(${order.id})" class="flex-1 bg-primary text-white py-3 rounded-xl font-bold text-sm transition-all hover:bg-primary-container active:scale-95">Xem chi tiết</button>
          ${order.trangThaiThanhToan !== "SUCCESS" ? `<button onclick="goToPayment(${order.id})" class="px-6 py-3 rounded-xl font-bold text-sm text-primary hover:bg-surface-container transition-all">Thanh toán</button>` : `<button onclick="goToFleet()" class="px-6 py-3 rounded-xl font-bold text-sm text-primary hover:bg-surface-container transition-all">Thuê lại</button>`}
        </div>
      </div>
    </div>`;
}

function getOrderStatus(status) {
  if (status === "PAID") return { label: "Đã thanh toán", className: "bg-primary-fixed text-on-primary-fixed" };
  if (status === "RENTING") return { label: "Đang thuê", className: "bg-secondary-container text-on-secondary-container" };
  if (status === "COMPLETED") return { label: "Hoàn tất", className: "bg-green-100 text-green-700" };
  if (status === "CANCELLED") return { label: "Đã hủy", className: "bg-error-container text-error" };
  return { label: "Chờ thanh toán", className: "bg-tertiary-fixed text-on-tertiary-fixed" };
}

function viewOrder(id) { window.location.href = `/payment.html?orderId=${id}`; }
function goToPayment(id) { window.location.href = `/payment.html?orderId=${id}`; }
function goToFleet() { window.location.href = "/fleet.html"; }
loadRentalHistory();
