<template>
  <Bar :data="chartData" :options="chartOptions" />
</template>

<script>
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import { Bar } from 'vue-chartjs'

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: 'MyPageConsultantGraphOne',
  components: {
    Bar
  },
  computed: {
    chartData() {
      return {
        labels: [ '나', '평균', '나', '평균' ],
        datasets: [
          {
            label: '예약 수',
            backgroundColor: ['#4FC3F7', '#E1F5FE', '#4FC3F7', '#E1F5FE'],
            borderColor: ['#039BE5', '#81D4FA', '#039BE5', '#81D4FA'],
            borderWidth: 1,
            data: this.$store.getters.getGraphOne
          },
        ]
      }
    },
    chartOptions() {
      return {
        responsive: true,
        maintainAspectRatio: false,
      }
    }
  },
}
</script>

<style scoped>

</style>