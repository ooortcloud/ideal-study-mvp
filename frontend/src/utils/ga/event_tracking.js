import ReactGA from 'react-ga4';

// 이벤트 트래킹
export const logEvent = (category, action, label, additionalData = {}) => {
  ReactGA.event({
    category: category,
    action: action,
    label: label,
    ...additionalData, // 추가 데이터 병합
  });
};

// 버튼 클릭 트래킹
export const trackButtonClick = (buttonName, location) => {
  logEvent('Button', 'Click', `${buttonName} - ${location}`);
};

// 폼 제출 트래킹
export const trackFormSubmit = (formName) => {
  logEvent('Form', 'Submit', formName);
};

// 사용자 로그인 트래킹 수정
export const trackLogin = (method, userEmail) => {
  logEvent('User', 'Login', method, {
    user_email: userEmail, // 사용자 이메일 추가
    login_method: method,
    timestamp: new Date().toISOString()
  });
}; 