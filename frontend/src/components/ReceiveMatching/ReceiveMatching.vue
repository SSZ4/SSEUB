<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-email-open</v-icon>
        <h2>전문가에게 받은 상담 제안 보기</h2>
      </div>
      <div class="page-inner-items border-sheet-four">
        <MoveCreateReservation v-if="reservations == null || reservations.length < 1" message="아직 받은 상담 제안이 없습니다."></MoveCreateReservation>
        <ReceivedCard v-for="(reservation, idx) in reservations" :reservation="reservation"
          :idx="idx" :key="idx" @deleteReservation="deleteReservation"
        />
      </div>
    </div>
  </div>
</template>

<script>
import ReceivedCard from "@/components/ReceiveMatching/ReceivedMatchingCard.vue";
import MoveCreateReservation from "@/components/CreateReservation/MoveCreateReservation.vue";
import NowLoading from "@/views/NowLoading.vue";
// import axios from "axios";
import { mapState } from "vuex";
import { apiInstance } from "@/api/index.js";
const userStore = "userStore";

export default {
  name: "ReceiveMatching",
  data: () => ({
    loaded: false,
    reservations:[], // [{value,[]},{value,[]}] 꼴
  }),
  computed: {
    ...mapState(userStore, ["userId"]),
  },
  components: {
    ReceivedCard,
    MoveCreateReservation,
    NowLoading
  },
  methods:{
    async getReservation() {
      console.log(`${process.env.VUE_APP_API_BASE_URL}/reservation/partner/unconfirm/${this.userId}`);
      const api = apiInstance();
      await api.get(`${process.env.VUE_APP_API_BASE_URL}/reservation/partner/unconfirm/${this.userId}`)
        .then(({ data }) => {
          //console.log("받은 상담 제안", data);
          for (var i = 0; i < data.length; i++) {
            let reservation = {};
            reservation["rno"] = data[i].reservationPet.rno;
            reservation["userId"] = data[i].reservationPet.userId;
            reservation["reservationDate"] = data[i].reservationPet.reservationDate;
            reservation["reservationConsultContent"] = data[i].reservationPet.reservationConsultContent;

            reservation["pno"] = data[i].reservationPet.pno;
            reservation["petImage"] = data[i].reservationPet.petImage;
            reservation["petType"] = data[i].reservationPet.petType;
            reservation["petName"] = data[i].reservationPet.petName;
            reservation["petVariety"] = data[i].reservationPet.petVariety;
            if(data[i].reservationPet.petBirth != null){
              reservation["petBirth"] = data[i].reservationPet.petBirth.substr(0,7);
            }
            else{
              reservation["petBirth"] = "생년월일 미상";
            }
            reservation["petInfo"] = data[i].reservationPet.petInfo;

            let matchingConsultants = [];
            for (let j = 0; j < data[i].matchingConsultants.length; j++){
              let matchingConsultant = {};
              matchingConsultant["consultantId"] = data[i].matchingConsultants[j].consultant_id;
              matchingConsultant["consultantIntro"] = data[i].matchingConsultants[j].consultant_intro;
              matchingConsultant["consultantName"] = data[i].matchingConsultants[j].consultant_name;
              matchingConsultant["consultantProfile"] = data[i].matchingConsultants[j].consultant_profile;
              matchingConsultant["consultantRate"] = data[i].matchingConsultants[j].consultant_rate;
              matchingConsultant["matchingComment"] = data[i].matchingConsultants[j].matching_comment;
              matchingConsultant["matchingCost"] = data[i].matchingConsultants[j].matching_cost;
              matchingConsultant["matchingNo"] = data[i].matchingConsultants[j].no;

              matchingConsultants.push(matchingConsultant);
            }
            //각각의 reservation안에 matchingConsultants라는 견적들의 리스트가 있다는 뜻
            reservation["matchingConsultants"] = matchingConsultants;
            this.reservations.push(reservation);
          }
          //console.log(this.reservations[0].matchingConsultants)
        })
        .catch((err) => {
          console.log(err);
        });
      return await Promise.resolve(true);
    },
    deleteReservation(idx) {
      this.reservations.splice(idx, 1);
    }
    // async deleteReservation(no) {
    //   //삭제 후 카운트 변경은 추후 생각해보자
    //   const api = apiInstance();
    //   await api
    //     .delete(process.env.VUE_APP_API_BASE_URL + `/` + no)
    //     .then(() => {
    //       console.log("삭제");
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
  },
  async created(){
    this.loaded = false;
    await this.getReservation().then((res) => {
      this.loaded = res;
    });
  }
};
</script>

<style></style>
