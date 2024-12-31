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
  }, [videoId]);

  return (
    <div className={styles.vimeoWrapper}>
      {/* 이 안에 VImeo Player에 의해 iframe 요소가 생긴다 */}
      <div id="myVideo" className={styles.vimeoPlayer}></div>
    </div>
  );
}

export default VimeoPlayer;
