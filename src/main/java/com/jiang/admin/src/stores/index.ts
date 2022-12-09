import {ref, computed, type Ref} from 'vue'
import {defineStore} from 'pinia'

export const useStore = defineStore('store', () => {

  const count = ref(0)
  const sideSwitch = ref(false)
  const doubleCount = computed(() => count.value * 2)

  async function increment() {
    count.value++
  }

  async function setCount(number: any) {
    count.value = count.value + number
  }

  function setSideSwitch() {
    sideSwitch.value = !sideSwitch.value
  }


  return {count, doubleCount, sideSwitch, increment, setCount, setSideSwitch}
})


