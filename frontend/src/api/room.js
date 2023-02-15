import { apiInstance } from "./index.js";
// import { BASE_APP_URL } from "@/uilts.js";

const token = localStorage.getItem("token");
const api = apiInstance();
async function joinRoomSession(roomId, success, fail) {
    //나중에 수정. login 정보와 기타등등을 넘겨야함.
    // console.log(loginInfo);d
    await api
      .post(`${process.env.VUE_APP_API_BASE_URL}/room/session`, {customSessionId: roomId},{  
          headers: {
            Authorization: `Bearer ${token}`
          },
      }) 
      .then(success)
      .catch(fail);
  } 

  export {joinRoomSession}   