import apiClient from "../apiClient.mjs";

/**
 * 로그인
 */
export const loginUser = async (username, password) => {
  console.log("로그인 API 시도:", username, password);
  try {
    const response = await apiClient.post("/auth/login", {
      username,
      password,
    });
    console.log("로그인 API 성공:", response);
    // 서버에서 받은 JWT 토큰과 유저 정보 반환

    const { token, username_server } = response.data; // 서버에서 반환된 데이터 구조에 맞게 수정 필요
    localStorage.setItem("jwtToken", token);

    return { username_server }; // 토큰과 유저 정보를 객체로 반환
  } catch (error) {
    console.log("로그인 API 실패", error);

    // 로그인 실패 시 임시 더미 데이터 반환
    const dummyData = {
      token: "dummy-token", // 더미 토큰
      user: { id: "1", name: "아기 한석원", role: "teacher", level: "3" }, // 더미 유저 정보
    };

    return dummyData;
  }
};

/**
 * 로그아웃
 */
export const logoutUser = async (username, password) => {
  console.log("로그아웃 API 시도:", username, password);
  try {
    const response = await apiClient.get("/auth/logout/{userId}");
    console.log("로그아웃 API 성공:", response);
    return response.data;
  } catch (err) {
    console.log("로그아웃 API 실패:", err);
  }
};
