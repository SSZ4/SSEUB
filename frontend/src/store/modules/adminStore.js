import { getUnCertifiedConsultantList } from "@/api/admin";
import { withdrawal } from "@/api/user";

const Swal = require("sweetalert2");

const adminStore = {
  namespaced: true,
  state: {
    consultantList: [], // # 자격 미인증 전문가 List
    // consultantDetail: {
    //   id: null,
    // },
  },
  getters: {
    // getConsultantDetail(state, index) {
    //   if (index < state.consultantList.length) {
    //     return state.consultantList[index];
    //   } else {
    //     return null;
    //   }
    // },
    getConsultantList(state) {
      return state.consultantList;
    },
  },
  mutations: {
    // UPDATE_CONSULTANT_DETAIL(state, payload) {
    //   state.consultantDetail = payload;
    //   console.log(
    //     "#UPDATE_CONSULTANT_DETAIL# 전문가 Detail 저장: ",
    //     state.consultantDetail
    //   );
    // },
    SET_CONSULTANT_LIST(state, consultantList) {
      state.consultantList = consultantList;
      // console.log(
      //   "#SET_CONSULTANT_LIST# 미검증 전문가 List 저장: ",
      //   state.consultantList
      // );
    },
  },
  actions: {
    // [@Method] 전문가 detail 페이지
    // updateConsultantDetail({ commit }, consultantDetail) {
    //   var consultantPetType = [];
    //   const petType = Array.from(consultantDetail.consultant_pet_type);
    //   for (var i = 0; i < 6; i++) {
    //     if (petType[i] == 1) {
    //       consultantPetType.push(i);
    //     }
    //   }
    //   consultantDetail.consultant_pet_type = consultantPetType;
    //   commit("UPDATE_CONSULTANT_DETAIL", consultantDetail);
    // },
    // [@Method] 자격증 미인증 전문가 List 조회
    async excuteGetUnCertifiedConsultantList({ commit }) {
      // console.log("#adminStore# 자격증 미인증 전문가 List 조회 동작");

      await getUnCertifiedConsultantList(
        ({ data }) => {
          // i) 조회 성공
          if (data.response == "success") {
            commit("SET_CONSULTANT_LIST", data.consultants);
          }
          // ii) 조회 실패
          else {
            // console.log(
            //   "#adminStore# 자격증 미인증 전문가 List 조회 실패: ",
            //   data
            // );
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] 관리자 - 회원 탈퇴 진행
    async excuteAdminWithdrawalUser({ commit }, id) {
      commit;
      console.log("#adminStore# 관리자 - 회원 탈퇴 진행 동작");

      const info = {
        id: id,
      };
      await withdrawal(
        info,
        ({ data }) => {
          // i) 회원탈퇴 성공
          if (data.response == "success") {
            // console.log("#adminStore# 관리자 - 회원탈퇴 성공: ", data);
            Swal.fire("SSEUB", `탈퇴처리 되었습니다.`, "success");
          }
          // ii) 회원탈퇴 실패
          else {
            // console.log("#adminStore# 관리자 - 회원탈퇴 실패: ", data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default adminStore;
