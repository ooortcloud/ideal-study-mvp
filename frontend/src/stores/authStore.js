import { create } from 'zustand';
import { parseJwtToken } from "../utils/auth/parseJwtToken";

const useAuthStore = create((set) => ({
  isAuthenticated: false,
  userInfo: {
    name: "",
    id: "",
    level: "",
    role: "",
  },
  
  // 로그인 처리
  login: (userId) => {
    const user = parseJwtToken(userId);
    
    if (user) {
      set({ 
        userInfo: user,
        isAuthenticated: true 
      });
      return user.id;
    }

    // 토큰이 없을 경우 userId만 설정
    set(state => ({ 
      userInfo: { ...state.userInfo, id: userId },
      isAuthenticated: true
    }));
  },
  
  // 로그아웃 처리
  logout: () => {
    set({ 
      isAuthenticated: false, 
      userInfo: {
        name: "",
        id: "",
        level: "",
        role: "",
      }
    });
    
    // 로컬 스토리지 클리어
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("user");
  },

  // 초기화 함수
  initialize: () => {
    const user = parseJwtToken();
    if (user) {
      set({
        isAuthenticated: true,
        userInfo: user
      });
    }
  }
}));

export default useAuthStore; 