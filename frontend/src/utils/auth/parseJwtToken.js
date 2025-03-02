// jwt에서 파싱하여 user객체를 반환하는 함수
export const parseJwtToken = (username) => {
  const token = localStorage.getItem("jwtToken");

  if (!token) return null;

  try {

    const payload = JSON.parse(atob(token.split(".")[1])); // JWT 페이로드 디코딩
    // console.log("payload좀 보자", payload);

    // 토큰 만료 확인
    // 추후 백엔드와 연동 필요
    const currentTime = Math.floor( Date.now() / 1000 ); // 현재 시간을 초 단위로 변환
    if (payload.exp && payload.exp < currentTime) {
      console.log("토큰이 만료되었습니다.");
      localStorage.removeItem("jwtToken");
      return null;
    }

    // Role 매핑
    let role;
    switch (payload.role) {
      case "ROLE_TEACHER":
        role = "teacher";
        break;
      case "ROLE_STUDENT":
        role = "student";
        break;
      case "ROLE_PARENTS":
        role = "parent";
        break;
      default:
        role = "";
    }

    return {
      id: payload.sub, // userId
      name: payload.name || "", // payload에 username 있을 경우 처리
      level: payload.level, // 필요에 따라 추가
      role, // 매핑된 role
    };
  } catch (error) {
    console.error("Invalid JWT Token:", error);
    return null;
  }
};
