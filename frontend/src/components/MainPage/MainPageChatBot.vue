<template>
	<!-- <div class="border-sheet-two chatbot">
		<v-icon icon="mdi-chat-alert-outline" size="50"></v-icon>
		<h3>SSUEB's Chatbot</h3>
		<v-btn
		>
			<p>챗봇에게 궁금한 점을 물어보세요!</p>
		</v-btn>
	</div> -->
	<v-hover>
		<template v-slot:default="{ isHovering, props }">
			<v-card class="border-sheet-two chatbot" @click="showMessenger()"
				:color="isHovering ? 'cyan-accent-4' : undefined" v-bind="props"
				style="cursor: pointer;"
			>
				<v-icon icon="mdi-chat-alert-outline" size="50"></v-icon>
				<h3>SSUEB's Chatbot</h3>
				<p>챗봇에게 궁금한 점을 물어보세요!</p>
			</v-card>
		</template>
	</v-hover>
</template>

<script>
import chatbotService from '@/api/chatbotService';

export default {
  name: "MainPageChatBot",
  methods:{
	showMessenger() {
		chatbotService.showMessenger();
	}
  }
  
}
</script>

<style>
.chatbot {
	height: 130px;
	width: 300px;

	display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>