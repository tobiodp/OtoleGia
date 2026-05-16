const vehicleId = getQueryParam("vehicleId");
const root = document.getElementById("vehicleDetailRoot");

async function loadVehicleDetail() {
  if (!vehicleId) {
    root.innerHTML = `<div class="bg-error-container rounded-2xl p-8 text-error font-bold">Thiếu vehicleId. Vui lòng quay lại trang Fleet.</div>`;
    return;
  }
  try {
    const v = await apiGet(`/vehicles/${vehicleId}`);
    const imageUrl = v.anhChinh || fallbackCarImage();
    root.innerHTML = `
      <section class="grid grid-cols-1 lg:grid-cols-12 gap-8">
        <div class="lg:col-span-8 space-y-8">
          <section class="grid grid-cols-4 grid-rows-2 gap-4 h-[400px] md:h-[600px]">
            <div class="col-span-4 md:col-span-3 row-span-2 rounded-2xl overflow-hidden shadow-lg group">
              <img alt="${safeText(v.tenXe)}" class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105" src="${imageUrl}" onerror="this.src='${fallbackCarImage()}'"/>
            </div>
            <div class="hidden md:block col-span-1 row-span-1 rounded-2xl overflow-hidden shadow-lg bg-surface-container">
              <img class="w-full h-full object-cover" src="${imageUrl}" onerror="this.src='${fallbackCarImage()}'"/>
            </div>
            <div class="hidden md:flex col-span-1 row-span-1 rounded-2xl overflow-hidden shadow-lg bg-primary items-center justify-center text-white">
              <span class="font-bold">+ More Photos</span>
            </div>
          </section>
          <div class="flex flex-wrap items-center justify-between gap-4">
            <div class="space-y-1">
              <div class="flex items-center gap-2">
                <span class="bg-secondary-container text-on-secondary-container text-[10px] font-bold px-3 py-1 rounded-full uppercase tracking-widest">${safeText(v.loaiXe, "Premium")}</span>
                <div class="flex items-center text-tertiary-container">
                  <span class="material-symbols-outlined text-sm filled-icon">star</span>
                  <span class="text-sm font-bold ml-1">4.9 (demo)</span>
                </div>
              </div>
              <h1 class="text-4xl md:text-5xl font-black tracking-tighter text-primary">${safeText(v.tenXe)}</h1>
              <p class="text-on-surface-variant max-w-2xl font-body leading-relaxed">${safeText(v.moTa, "Lựa chọn hoàn hảo cho các chuyến công tác và di chuyển cao cấp.")}</p>
            </div>
            <div class="bg-surface-container-lowest p-6 rounded-2xl shadow-sm flex flex-col items-end border-none">
              <span class="text-on-surface-variant text-xs font-bold uppercase tracking-widest">Giá thuê từ</span>
              <div class="flex items-baseline gap-1">
                <span class="text-3xl font-black text-primary">${formatMoney(v.giaThueNgay)}</span>
                <span class="text-on-surface-variant font-medium">/ngày</span>
              </div>
            </div>
          </div>
          <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
            ${spec("airline_seat_recline_extra","Số chỗ", safeText(v.soCho) + " chỗ")}
            ${spec("settings_input_component","Truyền động", safeText(v.hopSo))}
            ${spec("local_gas_station","Nhiên liệu", safeText(v.nhienLieu))}
            ${spec("badge","Biển số", safeText(v.bienSo))}
          </div>
        </div>
        <aside class="lg:col-span-4">
          <div class="sticky top-28 bg-surface-container-lowest p-8 rounded-2xl shadow-[0_12px_40px_rgba(0,31,63,0.06)] space-y-8 border-none">
            <div class="space-y-4">
              <h3 class="text-xl font-extrabold text-primary">Đặt lịch thuê xe</h3>
              <p class="text-sm text-on-surface-variant">Chủ sở hữu: <strong>${safeText(v.tenChuSoHuu)}</strong></p>
              <p class="text-sm text-on-surface-variant">Trạng thái: <strong>${safeText(v.trangThai)}</strong></p>
            </div>
            <div class="pt-6 border-t border-surface-container-high space-y-4">
              <div class="flex justify-between text-sm">
                <span class="text-on-surface-variant">Giá thuê/ngày</span>
                <span class="font-bold">${formatMoney(v.giaThueNgay)}</span>
              </div>
              <div class="flex justify-between items-center pt-4 border-t border-surface-container-high">
                <span class="text-lg font-bold">Dự kiến</span>
                <span class="text-2xl font-black text-tertiary-container">${formatMoney(v.giaThueNgay)}</span>
              </div>
            </div>
            <button onclick="goToBooking(${v.id})" class="w-full py-4 bg-gradient-to-r from-tertiary-container to-tertiary text-white rounded-xl font-bold tracking-tight shadow-lg hover:brightness-110 active:scale-95 transition-all flex items-center justify-center gap-2 uppercase text-sm">Xác nhận thuê xe <span class="material-symbols-outlined">arrow_forward</span></button>
          </div>
        </aside>
      </section>
    `;
  } catch (error) {
    root.innerHTML = `<div class="bg-error-container rounded-2xl p-8 text-error font-bold">${error.message}</div>`;
  }
}

function spec(icon, label, value) {
  return `<div class="bg-surface-container-low p-4 rounded-xl flex items-center gap-3">
    <div class="bg-white p-2 rounded-lg shadow-sm"><span class="material-symbols-outlined text-primary">${icon}</span></div>
    <div><p class="text-[10px] font-bold uppercase text-on-surface-variant tracking-tighter">${label}</p><p class="font-bold text-primary">${value}</p></div>
  </div>`;
}
function goToBooking(id) { window.location.href = `/booking.html?vehicleId=${id}`; }
loadVehicleDetail();
