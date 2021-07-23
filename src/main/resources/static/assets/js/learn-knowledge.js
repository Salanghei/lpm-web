layui.use(['config', 'element', 'laytpl', "rate"], function(){
    var $ = layui.jquery;
    var config = layui.config;
    var laytpl = layui.laytpl;
    var element = layui.element;
    var rate = layui.rate;
    //$.ajaxSettings.async = true;

    // 获取知识结构
    var graphChart = echarts.init(document.getElementById("getLearnerKnowledgeGraph"));
    graphChart.showLoading();
    console.log(config.base_server + 'knowledge/backgroundtest?access_token=' + config.getToken());
    $.get(config.base_server + 'knowledge/backgroundtest?access_token=' + config.getToken(), function(data){
        console.log("知识结构");

        var courses = data.children;
        for(var i = 0; i < courses.length; i ++){
            var r = Math.floor(Math.random() * 256);
            var g = Math.floor(Math.random() * 256);
            var b = Math.floor(Math.random() * 256);
            var color = "#" + r.toString(16) + g.toString(16) + b.toString(16);
            if(courses[i].value != null && courses[i].value > 0){
                //var borderWidth = Math.min((courses[i].value / data.max) * 10 + 1.5, 10);
                courses[i].value = courses[i].value.toFixed(2);
                var borderWidth = courses[i].value * 10 + 1.5;
                courses[i].itemStyle = {
                    borderColor: color,
                    borderWidth: borderWidth
                };
            }else{
                courses[i].itemStyle = {
                    borderColor: color,
                    borderWidth: 1.5
                };
            }
            changeColor(courses[i].children, color, data.max);
        }
        console.log(data);

        var option = {
            backgroundColor: '#ffffff',
            tooltip: {
                trigger: 'item',
                triggerOn: 'mousemove'
            },
            series: [
                {
                    type: 'tree',
                    data: [data],

                    top: '30',
                    left: '8%',
                    bottom: '30',
                    right: '30%',

                    /*top: '8%',
                    left: '8%',
                    bottom: '8%',
                    right: '8%',*/

                    //layout: 'radial',

                    symbolSize: 7,
                    initialTreeDepth: 1,

                    label: {
                        //show: false,
                        position: 'left',
                        verticalAlign: 'middle',
                        align: 'right'
                    },

                    leaves: {
                        label: {
                            //show: true,
                            position: 'right',
                            verticalAlign: 'middle',
                            align: 'left'
                        }
                    },

                    expandAndCollapse: true,
                    animationDuration: 550,
                    animationDurationUpdate: 750
                }
            ]
        };
        graphChart.hideLoading();
        graphChart.setOption(option);
    });

    graphChart.on("click", function(params){
        console.log(params.data.name);
        var container = document.getElementById("getLearnerKnowledgeGraph");
        var allNode = 0;
        var nodes = graphChart._chartsViews[0]._data._graphicEls;
        for(var i = 0,count = nodes.length; i < count; i ++){
            var node = nodes[i];
            if(node === undefined)
                continue;
            allNode++;
        }
        //var height = parseInt(container.style.height);
        var currentHeight = 20 * allNode;
        var newHeight = Math.max(currentHeight, 500);
        container.style.height = newHeight + 'px';
        graphChart.resize();
    });

    function parseData(data) {
        var courses = [];
        $.each(data, function(idx1, obj1){
            var chapters = [];
            $.each(obj1.chapters, function(idx2, obj2){
                var sections = [];
                $.each(obj2.sections, function(idx3, obj3){
                    var section = {};
                    section.name = obj3.section_name;
                    section.value = 0;
                    sections.push(section);
                });
                var chapter = {};
                chapter.name = obj2.chapter_name;
                chapter.children = sections;
                chapters.push(chapter);
            });
            var course = {};
            course.name = obj1.course_name;
            course.children = chapters;
            courses.push(course);
        });
        var result = {};
        result.name = "我";
        result.children = courses;
        return result;
    }

    function changeColor(list, color, max){
        $.each(list, function(i, obj){
            if(obj.value != null && obj.value > 0){
                //var borderWidth = Math.min((obj.value / max) * 10 + 1.5, 10);
                obj.value = obj.value.toFixed(2);
                var borderWidth = obj.value * 10 + 1.5;
                obj.itemStyle = {
                    borderColor: color,
                    borderWidth: borderWidth
                };
            }else{
                obj.itemStyle = {
                    borderColor: color,
                    borderWidth: 1.5
                };
            }
            if(obj.children != null && obj.children.length != 0){
                changeColor(obj.children, color, max);
            }
        });
    }
});