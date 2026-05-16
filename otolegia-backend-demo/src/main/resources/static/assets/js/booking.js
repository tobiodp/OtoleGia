const vehicleId = getQueryParam("vehicleId");
const bookingSummaryBox = document.getElementById("bookingSummaryBox");
const bookingSubmitBtn = document.getElementById("bookingSubmitBtn");
let selectedVehicle = null;

async function loadSelectedVehicle() {
  if (!vehicleId) {
    bookingSummaryBox.innerHTML = `<p class="text-red-200 font-semibold">Thiếu vehicleId. Vui lòng quay lại trang Fleet.</p>`;
    return;
  }
  try {
    selectedVehicle = await apiGet(`/vehicles/${vehicleId}`);
    bookingSummaryBox.innerHTML = `
      <div>
        <p class="text-xs font-bold text-slate-400 uppercase tracking-widest">Xe đang chọn</p>
        <h4 class="text-xl font-black text-white mt-1">${safeText(selectedVehicle.tenXe)}</h4>
        <p class="text-sm text-slate-400">${safeText(selectedVehicle.hangXe)} • ${safeText(selectedVehicle.loaiXe)}</p>
      </div>
      <div class="space-y-3 pt-6 border-t border-white/10">
        <div class="flex justify-between items-center"><span class="text-slate-400 text-sm">Giá thuê/ngày</span><span class="font-bold">${formatMoney(selectedVehicle.giaThueNgay)}</span></div>
        <div class="flex justify-between items-center"><span class="text-slate-400 text-sm">Sở hữu</span><span class="font-bold">${safeText(selectedVehicle.loaiChuSoHuu)}</span></div>
        <div class="flex justify-between items-center"><span class="text-slate-400 text-sm">Chủ xe</span><span class="font-bold text-right">${safeText(selectedVehicle.tenChuSoHuu)}</span></div>
        <div class="flex justify-between items-center pt-4 border-t border-white/20"><span class="text-lg font-bold">Tạm tính</span><span class="text-2xl font-black text-tertiary-fixed-dim">${formatMoney(selectedVehicle.giaThueNgay)}</span></div>
      </div>
    `;
  } catch (error) {
    bookingSummaryBox.innerHTML = `<p class="text-red-200 font-semibold">${error.message}</p>`;
  }
}

async function createRentalOrder() {
  try {
    const thoiGianNhan = document.getElementById("pickupDateTime").value;
    const thoiGianTra = document.getElementById("returnDateTime").value;
    const diaDiemNhan = document.getElementById("pickupLocation").value.trim();
    const diaDiemTra = document.getElementById("returnLocation").value.trim();

    if (!vehicleId) return alert("Thiếu vehicleId.");
    if (!thoiGianNhan || !thoiGianTra || !diaDiemNhan || !diaDiemTra) {
      return alert("Vui lòng nhập đầy đủ ngày nhận, ngày trả và địa điểm.");
    }

    bookingSubmitBtn.disabled = true;
    bookingSubmitBtn.textContent = "Đang tạo đơn thuê...";

    const order = await apiPost("/rental-orders", {
      customerId: DEMO_CUSTOMER_ID,
      vehicleId: Number(vehicleId),
      thoiGianNhan, thoiGianTra, diaDiemNhan, diaDiemTra
    });

    localStorage.setItem("lastOrderId", order.id);
    window.location.href = `/payment.html?orderId=${order.id}`;
  } catch (error) {
    alert(error.message);
  } finally {
    bookingSubmitBtn.disabled = false;
    bookingSubmitBtn.innerHTML = `Xác nhận thuê xe <span class="material-symbols-outlined">arrow_forward</span>`;
  }
}

bookingSubmitBtn.addEventListener("click", createRentalOrder);
loadSelectedVehicle();
