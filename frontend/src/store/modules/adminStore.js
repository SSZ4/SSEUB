import { getUnCertifiedConsultantList } from "@/api/admin";

const adminStore = {
  namespaced: true,
  state: {
    consultantList: null, // # 자격 미인증 전문가 List
    consultantDetail: {
      id: null,
    },
  },
  getters: {
    getConsultantDetail(state) {
      return state.consultantDetail;
    },
  },
  mutations: {
    UPDATE_CONSULTANT_DETAIL(state, payload) {
      state.consultantDetail = payload;
    },
    SET_CONSULTANT_LIST(state, consultantList) {
      state.consultantList = consultantList;
      console.log(
        "#SET_CONSULTANT_LIST# 미검증 전문가 List 저장: ",
        state.consultantList
      );
    },
  },
  actions: {
    // [@Method] 전문가 detail 페이지
    updateConsultantDetail({ commit }, consultantDetail) {
      commit("UPDATE_CONSULTANT_DETAIL", consultantDetail);
    },
    // [@Method] 자격증 미인증 전문가 List 조회
    async excuteGetUnCertifiedConsultantList({ commit }) {
      console.log("#adminStore# 자격증 미인증 전문가 List 조회 동작");

      await getUnCertifiedConsultantList(
        ({ data }) => {
          // i) 조회 성공
          if (data.response == "success") {
            commit("SET_CONSULTANT_LIST", data.consultants);
          }
          // ii) 조회 실패
          else {
            console.log(
              "#adminStore# 자격증 미인증 전문가 List 조회 실패: ",
              data
            );
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
