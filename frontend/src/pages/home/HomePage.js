import React from "react";
import { makeDummyUser } from "../../services/user/UserService.mjs";
import Button from "../../components/Button";
import useAuthStore from "../../stores/authStore";  // zustand store import

const HomePage = () => {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated);

  const makeDummy = async () => {
    await makeDummyUser();
  };

  return (
    <div>
      <main style={{ padding: "10px" }}>
        <Button onClick={makeDummy}> 더미유저 생성하기 </Button>
        <h1>메인 페이지1</h1>
        {isAuthenticated ? (
          <div>
            <p>로그인 상태입니다.</p>
          </div>
        ) : (
          <div>
            {" "}
            {/* 여러 요소를 감싸는 부모 div 추가 */}
            <p>비로그인 상태입니다.</p>
          </div>
        )}
      </main>
    </div>
  );
};

export default HomePage;
