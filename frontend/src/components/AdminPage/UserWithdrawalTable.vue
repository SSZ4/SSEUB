<template>
  <v-sheet class="mr-2" width="150">
    <v-select
      :items="['이메일', '이름', '닉네임']"
      variant="outlined"
      density="compact"
      v-model="range"
      hide-details
    ></v-select>
  </v-sheet>
  <v-text-field
    class="mb-1"
    v-model="keyword"
    density="compact"
    variant="outlined"
    label="검색"
    append-inner-icon="mdi-magnify"
    single-line
    hide-details
  ></v-text-field>
  <v-table fixed-header hover>
    <thead>
      <tr>
        <th width="96">번호</th>
        <th width="320">이메일</th>
        <th width="180">이름</th>
        <th width="180">닉네임</th>
        <th width="150">권한</th>
        <th width="90"></th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="(user, index) in getSearchList"
        :key="index"
        style="cursor: pointer"
      >
        <td>{{ String(index).padStart(4, "0") }}</td>
        <td>{{ user.id }}</td>
        <td>{{ user.userName }}</td>
        <td>{{ user.userNickname }}</td>
        <td>{{ user.authorities[0].authorityName.substr(5, 10) }}</td>
        <td>
          <v-btn
            class="mr-3"
            variant="outlined"
            color="error"
            rounded="0"
            @click="withdrawal(user.id, index)"
          >
            탈퇴
          </v-btn>
        </td>
      </tr>
    </tbody>
  </v-table>
</template>

<script>
// const userStore = "userStore";
const adminStore = "adminStore";
import { mapActions } from "vuex";

export default {
  name: "UserWithdrawalTable",
  props: {
    userList: Array,
  },
  data() {
    return {
      range: "이메일",
      keyword: "",
    };
  },
  computed: {
    getSearchList() {
      if (this.range == "이메일") {
        return this.userList.filter((v) => v.id.includes(this.keyword));
      } else if (this.range == "이름") {
        return this.userList.filter((v) => v.userName.includes(this.keyword));
      } else if (this.range == "닉네임") {
        return this.userList.filter(
          (v) => v.userNickname != null && v.userNickname.includes(this.keyword)
        );
      } else {
        return this.userList;
      }
    },
  },
  methods: {
    ...mapActions(adminStore, ["excuteAdminWithdrawalUser"]),
    // [@Method] 관리자 - 회원탈퇴
    async withdrawal(id, index) {
      this.$swal
        .fire({
          title: "탈퇴하시겠습니까?",
          text: `탈퇴 시 ${id} 계정은 사용하지 못하게 됩니다.`,
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Yes",
        })
        .then(async (result) => {
          if (result.isConfirmed) {
            await this.excuteAdminWithdrawalUser(id);
            this.$emit("deleteRemoveUser", index);
            // location.reload();
          }
        });
    },
  },
};
</script>

<style></style>
