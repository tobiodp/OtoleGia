const orderId = getQueryParam("orderId") || localStorage.getItem("lastOrderId");
const paymentOrderBox = document.getElementById("paymentOrderBox");
const paymentSummaryBox = document.getElementById("paymentSummaryBox");
const paymentSubmitBtn = document.getElementById("paymentSubmitBtn");

const BANK_ID = "MB";
const BANK_NAME = "MB Bank";
const ACCOUNT_NO = "123456789";
const ACCOUNT_NAME = "CONG TY OTOLEGIA";
const QR_TEMPLATE = "compact2";

async function loadOrderForPayment() {
  if (!orderId) {
    alert("Không tìm thấy orderId. Vui lòng đặt xe trước.");
    window.location.href = "/fleet.html";
    return;
  }
  try {
    const order = await apiGet(`/rental-orders/${orderId}`);
    renderOrder(order);
    renderPaymentSummary(order);
    renderVietQr(order);
    if (order.trangThaiThanhToan === "SUCCESS") {
      paymentSubmitBtn.disabled = true;
      paymentSubmitBtn.textContent = "Đơn này đã thanh toán";
      paymentSubmitBtn.classList.add("opacity-60", "cursor-not-allowed");
    }
  } catch (error) {
    paymentOrderBox.innerHTML = `<p class="text-error font-semibold">${error.message}</p>`;
    paymentSummaryBox.innerHTML = `<p class="text-error font-semibold">Không thể tải thanh toán.</p>`;
  }
}

function renderOrder(order) {
  paymentOrderBox.innerHTML = `
    <div class="flex flex-col md:flex-row gap-8">
      <div class="w-full md:w-1/3 aspect-[16/10] rounded-lg overflow-hidden bg-surface-container-low relative">
        <img alt="Executive Car" class="w-full h-full object-cover" src="/images/default-car.jpg"/>
        <div class="absolute top-4 left-4"><span class="bg-primary text-white text-[10px] font-bold px-3 py-1 rounded-full uppercase tracking-widest">Executive Fleet</span></div>
      </div>
      <div class="flex-1">
        <h2 class="font-headline text-2xl font-bold text-primary mb-1">${order.tenXe}</h2>
        <p class="text-on-surface-variant text-sm mb-6">${order.hangXe} • Mã đơn: ${order.maDonThue}</p>
        <div class="grid grid-cols-2 gap-y-4">
          <div><p class="text-xs text-on-surface-variant uppercase tracking-wider mb-1 font-semibold">Nhận xe</p><p class="font-headline font-bold text-sm">${formatDateTime(order.thoiGianNhan)}</p><p class="text-xs text-slate-500">${order.diaDiemNhan}</p></div>
          <div><p class="text-xs text-on-surface-variant uppercase tracking-wider mb-1 font-semibold">Trả xe</p><p class="font-headline font-bold text-sm">${formatDateTime(order.thoiGianTra)}</p><p class="text-xs text-slate-500">${order.diaDiemTra}</p></div>
        </div>
      </div>
    </div>
  `;
}

function renderPaymentSummary(order) {
  paymentSummaryBox.innerHTML = `
    <div class="flex justify-between items-center text-sm font-medium"><span class="text-on-surface-variant">Đơn giá/ngày</span><span class="text-primary font-headline">${formatMoney(order.donGiaNgay)}</span></div>
    <div class="flex justify-between items-center text-sm font-medium"><span class="text-on-surface-variant">Số ngày thuê</span><span class="text-primary font-headline">${order.soNgayThue}</span></div>
    <div class="flex justify-between items-center text-sm font-medium"><span class="text-on-surface-variant">Trạng thái</span><span class="text-primary font-headline">${order.trangThai} / ${order.trangThaiThanhToan}</span></div>
    <div class="pt-6 border-t border-surface-container flex justify-between items-end"><div><p class="text-xs font-bold text-on-surface-variant uppercase tracking-widest">Tổng cộng</p><p class="font-headline text-2xl font-black text-primary">${formatMoney(order.tongTien)}</p></div></div>
  `;
}

function renderVietQr(order) {
  const amount = Math.round(Number(order.tongTien));
  const transferContent = order.maDonThue ? order.maDonThue.replace(/[^a-zA-Z0-9]/g, "").substring(0, 25) : ("DT" + order.id);
  const qrUrl = `https://img.vietqr.io/image/${BANK_ID}-${ACCOUNT_NO}-${QR_TEMPLATE}.png?amount=${amount}&addInfo=${encodeURIComponent(transferContent)}&accountName=${encodeURIComponent(ACCOUNT_NAME)}`;

  document.getElementById("vietQrImage").src = qrUrl;
  document.getElementById("qrBankName").textContent = BANK_NAME;
  document.getElementById("qrAccountNo").textContent = ACCOUNT_NO;
  document.getElementById("qrAccountName").textContent = ACCOUNT_NAME;
  document.getElementById("qrAmount").textContent = formatMoney(amount);
  document.getElementById("qrContent").textContent = transferContent;
  document.getElementById("qrPaymentBox").classList.remove("hidden");
}

async function payOrder() {
  try {
    paymentSubmitBtn.disabled = true;
    paymentSubmitBtn.textContent = "Đang xác nhận thanh toán...";
    await apiPost("/payments", {
      rentalOrderId: Number(orderId),
      phuongThuc: "BANK_TRANSFER",
      maGiaoDich: "GD_WEB_" + Date.now()
    });
    alert("Thanh toán thành công! Đơn thuê đã được cập nhật.");
    window.location.href = `/rental-history.html?customerId=${DEMO_CUSTOMER_ID}`;
  } catch (error) {
    alert(error.message);
    paymentSubmitBtn.disabled = false;
    paymentSubmitBtn.textContent = "Tôi đã thanh toán - Xác nhận đơn";
  }
}

paymentSubmitBtn.addEventListener("click", payOrder);
loadOrderForPayment();
