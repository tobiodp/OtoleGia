const vehicleGrid = document.getElementById("vehicleGrid");
const vehicleCount = document.getElementById("vehicleCount");
const keywordInput = document.getElementById("keywordInput");
const brandInput = document.getElementById("brandInput");
const typeInput = document.getElementById("typeInput");
const seatsInput = document.getElementById("seatsInput");
const minPriceInput = document.getElementById("minPriceInput");
const maxPriceInput = document.getElementById("maxPriceInput");
const searchBtn = document.getElementById("searchBtn");
const resetBtn = document.getElementById("resetBtn");

async function loadVehicles() {
  try {
    vehicleGrid.innerHTML = `<div class="col-span-full bg-surface-container-lowest rounded-2xl p-10 text-center text-on-surface-variant font-semibold shadow-sm">Đang tải danh sách xe...</div>`;
    const params = new URLSearchParams();
    appendParam(params, "keyword", keywordInput.value.trim());
    appendParam(params, "brand", brandInput.value.trim());
    appendParam(params, "type", typeInput.value.trim());
    appendParam(params, "seats", seatsInput.value.trim());
    appendParam(params, "minPrice", minPriceInput.value.trim());
    appendParam(params, "maxPrice", maxPriceInput.value.trim());

    let url = "/vehicles";
    if (params.toString()) url += "?" + params.toString();

    const vehicles = await apiGet(url);
    vehicleCount.textContent = vehicles.length;

    if (!vehicles.length) {
      vehicleGrid.innerHTML = `<div class="col-span-full bg-surface-container-lowest rounded-2xl p-10 text-center text-on-surface-variant font-semibold shadow-sm">Không tìm thấy xe phù hợp.</div>`;
      return;
    }

    vehicleGrid.innerHTML = vehicles.map(renderVehicleCard).join("");
  } catch (error) {
    vehicleGrid.innerHTML = `<div class="col-span-full bg-error-container text-on-error-container rounded-2xl p-10 text-center font-semibold">${error.message}</div>`;
  }
}

function appendParam(params, name, value) {
  if (value !== "") params.append(name, value);
}

function renderVehicleCard(vehicle) {
  const imageUrl = vehicle.anhChinh || fallbackCarImage();
  const ownerBadge = vehicle.loaiChuSoHuu === "PARTNER" ? "Partner Fleet" : "Executive Fleet";
  return `
    <div class="group bg-surface-container-lowest rounded-2xl overflow-hidden shadow-sm hover:shadow-[0_12px_40px_rgba(0,31,63,0.06)] transition-all duration-500">
      <div class="relative h-64 overflow-hidden">
        <img class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700" src="${imageUrl}" alt="${safeText(vehicle.tenXe)}" onerror="this.src='${fallbackCarImage()}'"/>
        <div class="absolute top-4 left-4 flex flex-wrap gap-2">
          <span class="bg-white/90 backdrop-blur-md text-[10px] font-black uppercase tracking-widest px-3 py-1 rounded-full text-primary">${safeText(vehicle.loaiXe, "Premium")}</span>
          <span class="bg-primary text-white text-[10px] font-black uppercase tracking-widest px-3 py-1 rounded-full">${ownerBadge}</span>
        </div>
      </div>
      <div class="p-6 space-y-4">
        <div class="flex justify-between items-start gap-4">
          <div>
            <h3 class="font-headline font-bold text-xl text-primary">${safeText(vehicle.tenXe)}</h3>
            <p class="text-sm text-outline font-medium">${safeText(vehicle.hangXe)} • ${safeText(vehicle.namSanXuat)}</p>
          </div>
          <div class="text-right">
            <p class="text-[10px] text-outline uppercase tracking-widest font-bold">Từ</p>
            <p class="font-headline font-extrabold text-lg text-primary">${formatMoney(vehicle.giaThueNgay)}</p>
            <p class="text-[10px] text-on-surface-variant">/ngày</p>
          </div>
        </div>
        <div class="grid grid-cols-3 gap-4 pt-4 border-t border-outline-variant/10">
          <div class="flex flex-col items-center gap-1"><span class="material-symbols-outlined text-on-primary-container scale-75">airline_seat_recline_extra</span><span class="text-[10px] font-bold text-outline-variant uppercase">${safeText(vehicle.soCho)} chỗ</span></div>
          <div class="flex flex-col items-center gap-1"><span class="material-symbols-outlined text-on-primary-container scale-75">local_gas_station</span><span class="text-[10px] font-bold text-outline-variant uppercase">${safeText(vehicle.nhienLieu)}</span></div>
          <div class="flex flex-col items-center gap-1"><span class="material-symbols-outlined text-on-primary-container scale-75">settings_input_component</span><span class="text-[10px] font-bold text-outline-variant uppercase">${safeText(vehicle.hopSo)}</span></div>
        </div>
        <p class="text-sm text-on-surface-variant leading-relaxed line-clamp-2">${safeText(vehicle.moTa, "Trải nghiệm di chuyển cao cấp cùng OTOLEGIA.")}</p>
        <div class="grid grid-cols-2 gap-3 pt-2">
          <button onclick="viewVehicle(${vehicle.id})" class="bg-surface-container hover:bg-surface-container-high py-3 rounded-xl font-bold text-sm transition-all">Chi tiết</button>
          <button onclick="goToBooking(${vehicle.id})" class="bg-gradient-to-br from-tertiary-container to-tertiary text-white py-3 rounded-xl font-bold text-sm shadow-lg hover:scale-[1.02] active:scale-95 transition-all">Đặt xe</button>
        </div>
      </div>
    </div>
  `;
}

function viewVehicle(id) { window.location.href = `/vehicle-detail.html?vehicleId=${id}`; }
function goToBooking(id) { window.location.href = `/booking.html?vehicleId=${id}`; }

function resetFilters() {
  [keywordInput, brandInput, typeInput, seatsInput, minPriceInput, maxPriceInput].forEach(i => i.value = "");
  loadVehicles();
}

searchBtn.addEventListener("click", loadVehicles);
resetBtn.addEventListener("click", resetFilters);
[keywordInput, brandInput, typeInput, seatsInput, minPriceInput, maxPriceInput].forEach(input => {
  input.addEventListener("keydown", e => { if (e.key === "Enter") loadVehicles(); });
});
loadVehicles();
