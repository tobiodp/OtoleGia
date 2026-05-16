const API_BASE_URL = "/api";
const DEMO_CUSTOMER_ID = 1;

function formatMoney(value) {
  if (value === null || value === undefined || value === "") return "0 VNĐ";
  return Number(value).toLocaleString("vi-VN") + " VNĐ";
}

function formatDateTime(value) {
  if (!value) return "";
  const date = new Date(value);
  return date.toLocaleString("vi-VN", {
    hour: "2-digit",
    minute: "2-digit",
    day: "2-digit",
    month: "2-digit",
    year: "numeric"
  });
}

function getQueryParam(name) {
  return new URLSearchParams(window.location.search).get(name);
}

async function apiGet(path) {
  const response = await fetch(API_BASE_URL + path);
  const result = await response.json();
  if (!response.ok || result.success === false) {
    throw new Error(result.message || "Lỗi gọi API GET");
  }
  return result.data;
}

async function apiPost(path, body) {
  const response = await fetch(API_BASE_URL + path, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body)
  });
  const result = await response.json();
  if (!response.ok || result.success === false) {
    throw new Error(result.message || "Lỗi gọi API POST");
  }
  return result.data;
}

function fallbackCarImage() {
  return "/images/default-car.jpg";
}

function safeText(value, fallback = "-") {
  return value === null || value === undefined || value === "" ? fallback : value;
}
