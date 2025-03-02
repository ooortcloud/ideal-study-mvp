import React, { useEffect } from "react";
import Player from "@vimeo/player";
import styles from "./VimeoPlayer.module.css";

function VimeoPlayer({ videoId = "1037702745" }) {

  useEffect(() => {
    const player = new Player("myVideo", {
      url: `https://vimeo.com/${videoId}`,
      responsive: true,
    });

    // 플레이어 이벤트 리스너 추가 (예: 준비 완료 이벤트)
    player.on("loaded", () => {
      console.log("Vimeo player is ready");
    });

    // 에러 핸들링
    player.on("error", (error) => {
      console.error("Vimeo player error:", error);
    });

    // 재생 시간 추적
    player.on("timeupdate", ({ seconds, percent, duration }) => {
      console.log("현재 재생 시간:", seconds);
      console.log("진행률:", percent * 100, "%");
      console.log("전체 길이:", duration, "초");
      
      // 여기에서 진행 상황을 저장하거나 처리할 수 있습니다
      // 예: API 호출이나 상태 업데이트
    });

    // 동영상 종료 이벤트
    player.on("ended", () => {
      console.log("동영상 시청 완료");
      // 시청 완료 처리 로직
    });

    // 컴포넌트 언마운트 시 이벤트 리스너 정리
    return () => {
      player.off("timeupdate");
      player.off("ended");
    };
  }, [videoId]);

  return (
    <div className={styles.vimeoWrapper}>
      {/* 이 안에 VImeo Player에 의해 iframe 요소가 생긴다 */}
      <div id="myVideo" className={styles.vimeoPlayer}></div>
    </div>
  );
}

export default VimeoPlayer;
