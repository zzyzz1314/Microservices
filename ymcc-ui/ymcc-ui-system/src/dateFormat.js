// src/utils/dateFormat.js 完整代码
export function formatDateTime(time) {
    if (!time) return ''; // 处理空值，避免报错
    const date = new Date(time);
    const year = date.getFullYear();
    // 月份+1并补0（0=1月，11=12月）
    const month = String(date.getMonth() + 1).padStart(2, '0');
    // 日期补0（getDate() 获取当月第几天，1-31）
    const day = String(date.getDate()).padStart(2, '0');
    // 小时补0
    const hours = String(date.getHours()).padStart(2, '0');
    // 分钟补0
    const minutes = String(date.getMinutes()).padStart(2, '0');
    // 秒数补0（可选，根据需求决定是否保留）
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}