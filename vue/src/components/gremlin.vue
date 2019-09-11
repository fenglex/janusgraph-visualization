<template>
	<div class="gremlin">
		<div>
			<el-card class="box-card">
				<div class="text item" style="padding-bottom: 5px;">
					<el-input type="textarea" v-model="gremlin" rows="5" resize="none"></el-input>
				</div>
				<el-row>
					<div>
						<el-col :span="4">
							<div>
								<el-input v-model="host" size="small">
									<template slot="prepend">地址</template>
								</el-input>
							</div>
						</el-col>
						<el-col :span="4">
							<div>
								<el-input v-model="port" size="small">
									<template slot="prepend">端口</template>
								</el-input>
							</div>
						</el-col>
						<el-col :span="1" :offset="14">
							<div>
								<el-button size="small" style="margin-top: 5px;" v-on:click="query" type="primary">执行</el-button>
							</div>
						</el-col>
					</div>
				</el-row>
			</el-card>
		</div>
		<div style="padding-top: 5px;">
			<el-col>
				<el-col :span="6">
					<el-card class="box-card" id="result">
						<div slot="header" class="clearfix">
							<span>查询结果</span>
						</div>
						<div class="text item" v-html="gremlinResult" id="result-data">

						</div>
					</el-card>
				</el-col>
				<el-col :span="18" style="padding-left: 5px;">
					<el-card id="graph" class="box-card">

					</el-card>
				</el-col>
			</el-col>
		</div>
		<div>
			<a style="padding-left: 30px;" href="https://github.com/fenglex/janusgraph-visualization">使用说明https://github.com/fenglex/janusgraph-visualization</a>
		</div>
	</div>

</template>

<script>
	import axios from 'axios'
	import {
		Notification
	} from 'element-ui';
	export default {
		name: 'gremlin',
		data: function() {
			return {
				name: 'haifeng',
				host: 'localhost',
				port: '8182',
				gremlin: '',
				gremlinResult: ''
			};
		},
		mounted: function() {
			let wh = document.documentElement.clientHeight;
			let eh = 247;
			let ch = (wh - eh) + "px";
			document.getElementById("result").style.minHeight = ch;
			let c = 247;
			let gh = (wh - c) + "px";
			document.getElementById("graph").style.minHeight = gh;
		},
		methods: {
			query: function() {
				axios.get('/query', {
						params: {
							host: this.$data.host,
							port: this.$data.port,
							gremlin: this.$data.gremlin
						}
					}).then(res => {
						var result = res.data;
						this.$data.gremlinResult = result.result.replace(/\n/g, "<br/>");
						var myChart = this.$echarts.init(document.getElementById('graph'));
						var option = {
							title: {
								text: ''
							},
							tooltip: {
								formatter: function(param) {
									return "id:" + param.data.id + "<br/>" + "label:" + param.data.label;
								}
							},
							animationDurationUpdate: 1500,
							animationEasingUpdate: 'quinticInOut',
							label: {
								normal: {
									show: true,
									textStyle: {
										fontSize: 12
									},
								}
							},
							series: [{
								type: 'graph',
								layout: 'force',
								symbol: 'circle',
								symbolSize: 45,
								edgeSymbolSize: [4, 10],
								focusNodeAdjacency: true,
								edgeSymbol: ['circle', 'arrow'],
								categories: [{
									itemStyle: {
										normal: {
											color: "#009800",
										}
									}
								}],
								roam: true,
								label: {
									normal: {
										show: true,
										textStyle: {
											fontSize: 10
										},
										formatter: function(param) {
											return param.data.label;
										}
									}
								},
								force: {
									repulsion: 1000
								},
								edgeLabel: {
									normal: {
										show: true,
										textStyle: {
											fontSize: 10
										},
										formatter: function(param) {
											return param.data.label;
										}
									}
								},
								data: result.vertices,
								links: result.edges,
								lineStyle: {
									normal: {
										type: 'solid',
										opacity: 1,
										width: 2,
										curveness: 0
									}
								}
							}]
						};
						// 使用刚指定的配置项和数据显示图表。
						myChart.setOption(option);

						myChart.on('click', function(params) {
							Notification.closeAll();
							var title = "id:" + params.data.id + ",label:" + params.data.label;
							var c = "";
							var props = params.data.properties;
							for (var i = 0; i < props.length; i++) {
								c += props[i].key + ":" + props[i].value + "<br/>";
							}
							Notification({
								title: title,
								message: c,
								dangerouslyUseHTMLString: true,
								duration: 500000,
								customClass: 'prop-box',
								position: 'bottom-right'
							});

						});
					})
					.catch(function() {

					});
			}
		}
	}
</script>

<style>
	.prop-box{
		min-width: 150px;
	}
</style>
