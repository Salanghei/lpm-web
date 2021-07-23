layui.use(['config', 'element', "laytpl", 'form'], function(){
    var $ = layui.jquery;
    var config = layui.config;
    var element = layui.element;
    var laytpl = layui.laytpl;
    var form = layui.form;
    element.init();
    form.render();

    form.on('submit(choose-id)', function (data) {
        var myChart = echarts.init(document.getElementById('student-video-behavior'));
        myChart.showLoading();
        $.get(config.base_server + 'tag/studentVideoBehavior?access_token=' + config.getToken() + "&studentId=" + data.field.studentId, function (data) {
            var nodes = data.nodes;
            var edges = data.edges;
            var categories = data.categories;
            console.log(nodes);
            console.log(edges);
            console.log(categories);
            var option = {
                backgroundColor: '#ffffff',
                legend: [{
                    // selectedMode: 'single',
                    data: categories
                }],
                animationDuration: 1500,
                animationEasingUpdate: 'quinticInOut',
                series : [
                    {
                        type: 'graph',
                        layout: 'none',
                        data: nodes,
                        links: edges,
                        categories: categories.map(function (a) { return {'name': a}; }),
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
                            show:'true',
                            position: 'bottom',
                            rotate: 30,
                            formatter: function (params) {
                                return params.data.chapterName;
                            }
                        },
                        edgeSymbol: ['circle', 'arrow'],
                        lineStyle: {
                            color: 'source',
                            curveness: 0.2
                        },
                        emphasis: {
                            lineStyle: {
                                width: 10
                            }
                        }
                    }
                ]
            };
            myChart.hideLoading();
            myChart.setOption(option);
        });
        return false;
    });
});