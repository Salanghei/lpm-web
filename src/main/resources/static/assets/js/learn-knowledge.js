layui.use(['config', 'element', 'laytpl', "rate"], function(){
    var $ = layui.jquery;
    var config = layui.config;
    var laytpl = layui.laytpl;
    var element = layui.element;
    var rate = layui.rate;
    $.ajaxSettings.async = true;

    // 知识表格
    $.get(config.base_server + 'knowledge?page=0&limit=36&access_token=' + config.getToken(), function (res) {
        //第三步：渲染模版
        var nodes = [ //数据
            {"knowledge_node": "计算机网络", "score": 1.5},
            {"knowledge_node": "网络协议", "score": 2},
            {"knowledge_node": "网络体系结构", "score": 3},
            {"knowledge_node": "计算机网络结构", "score": 3},
            {"knowledge_node": "电路交换", "score": 3},
            {"knowledge_node": "多路复用", "score": 3},
            {"knowledge_node": "速率", "score": 3.5},
            {"knowledge_node": "带宽", "score": 4},
            {"knowledge_node": "延迟", "score": 3},
            {"knowledge_node": "时延带宽积", "score": 2},
            {"knowledge_node": "OSI参考模型", "score": 3},
            {"knowledge_node": "Internet发展历史", "score": 3},
            {"knowledge_node": "网络应用层", "score": 4},
            {"knowledge_node": "网络应用体系结构", "score": 3},
            {"knowledge_node": "网络应用进程通信", "score": 3},
            {"knowledge_node": "网络应用需求", "score": 3},
            {"knowledge_node": "应用体系结构", "score": 3},
            {"knowledge_node": "应用进程通信", "score": 3},
            {"knowledge_node": "HTTP连接类型", "score": 2},
            {"knowledge_node": "HTTP消息格式", "score": 3},
            {"knowledge_node": "Cookie技术", "score": 3},
            {"knowledge_node": "Web缓存技术", "score": 3},
            {"knowledge_node": "Web应用概述", "score": 3},
            {"knowledge_node": "Email应用概述", "score": 3},
            {"knowledge_node": "Email消息格式", "score": 3},
            {"knowledge_node": "POP协议", "score": 1.5},
            {"knowledge_node": "DNS概述", "score": 3},
            {"knowledge_node": "吞吐量", "score": 3},
            {"knowledge_node": "Socket编程", "score": 3},
            {"knowledge_node": "传输层服务", "score": 3},
            {"knowledge_node": "复用和分用", "score": 1.5},
            {"knowledge_node": "无线连接传输协议", "score": 3},
            {"knowledge_node": "可靠数据传输", "score": 3},
            {"knowledge_node": "rdt2.0", "score": 3},
            {"knowledge_node": "rdt2.1", "score": 3},
            {"knowledge_node": "web应用概述", "score": 3.5},
            {"knowledge_node": "TCP概述", "score": 3},
            {"knowledge_node": "TCP可靠数据传输", "score": 3},
            {"knowledge_node": "TCP流量控制", "score": 4},
            {"knowledge_node": "TCP连接管理", "score": 3},
            {"knowledge_node": "拥塞控制原理", "score": 3},
            {"knowledge_node": "丢包率", "score": 5}
        ];
        if (res.data == null || res.data.length == 0) res.data = nodes;
        var getKnowledgeTableTpl = document.getElementById("knowledge-table").innerHTML;
        var getKnowledgeTableView = document.getElementById("knowledge-table-box");
        laytpl(getKnowledgeTableTpl).render(res.data, function (html) {
            getKnowledgeTableView.innerHTML = html;
            $('.rate').each(function() {
                var o = this;
                //console.log(o.innerHTML);
                rate.render({
                    elem: o,
                    value: o.innerHTML,
                    readonly: true
                });
            });
        });
    });

    // 获取知识地图
    var graphChart = echarts.init(document.getElementById("getLearnerKnowledgeGraph"), {width: 900});
    graphChart.showLoading();
    $.get('../../assets/data/les-miserables.gexf', function (xml) {
        var graph = echarts.dataTool.gexf.parse(xml);
        var categories = [];
        for (var i = 0; i < 9; i++) {
            categories[i] = {
                name: '类目' + i
            };
        }
        graph.nodes.forEach(function (node) {
            node.itemStyle = null;
            node.value = node.symbolSize;
            node.symbolSize /= 1.5;
            node.label = {
                normal: {
                    show: node.symbolSize > 30
                }
            };
            node.category = node.attributes.modularity_class;
        });
        var option = {
            backgroundColor: '#ffffff',
            tooltip: {},
            animationDuration: 1500,
            animationEasingUpdate: 'quinticInOut',
            series : [
                {
                    name: '知识结点',
                    type: 'graph',
                    layout: 'none',
                    data: graph.nodes,
                    links: graph.links,
                    categories: categories,
                    roam: true,
                    focusNodeAdjacency: true,
                    itemStyle: {
                        normal: {
                            borderColor: '#fff',
                            borderWidth: 1,
                            shadowBlur: 10,
                            shadowColor: 'rgba(0, 0, 0, 0.3)'
                        }
                    },
                    label: {
                        position: 'right',
                        formatter: '{b}'
                    },
                    lineStyle: {
                        color: 'source',
                        curveness: 0.3
                    },
                    emphasis: {
                        lineStyle: {
                            width: 10
                        }
                    }
                }
            ]
        };
        graphChart.hideLoading();
        graphChart.setOption(option);
    }, 'xml');
});