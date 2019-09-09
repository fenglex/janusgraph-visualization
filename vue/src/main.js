import Vue from 'vue'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import echarts from 'echarts'

Vue.prototype.$echarts = echarts
Vue.use(ElementUI);
Vue.config.productionTip = false


import axios from 'axios'
Vue.prototype.$axios = axios

new Vue({
  el: '#app',
  render: h => h(App),
  
}).$mount('#app')
