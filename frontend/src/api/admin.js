import { apiInstance } from "./index.js";
import router from "@/router/index.js";

const api = apiInstance();
const Swal = require("sweetalert2");

// [POST] 전문가 회원가입 수락
async function acceptConsultant(id) {
  const data = {};
  data.id = id;
  // console.log(data)
  await api.post(
    `${process.env.VUE_APP_API_BASE_URL}/user/join/consultant/accept`, JSON.stringify(data)
  )
  .then((res) => {
    console.log(res.data)
    if (res.data.response == "success") {
      Swal.fire(
        "회원가입 수락",
        "회원가입을 수락하고, 수락 알림을 보냈습니다.",
        "success"
      );
      router.go(-1);
    } else {
      Swal.fire(
        "회원가입 수락 실패",
        "다시 시도해 주시기 바랍니다.",
        "error"
      );
    }
  })
}

export { acceptConsultant };