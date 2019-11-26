import Vue from 'vue'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import vis from 'vis-network'

Vue.use(ElementUI);
Vue.config.productionTip = false


import axios from 'axios'
Vue.prototype.$axios = axios
Vue.prototype.$base_url=''
new Vue({
  el: '#app',
  render: h => h(App),
  
}).$mount('#app')
