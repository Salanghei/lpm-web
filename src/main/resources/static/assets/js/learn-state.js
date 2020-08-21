layui.use(['config', 'element', 'form', 'laytpl'], function(){
    var $ = layui.jquery;
    var config = layui.config;
    var element = layui.element;
    var form = layui.form;
    var laytpl = layui.laytpl;

    $.ajaxSettings.async = true;

    // 获取学习状态总体情况
    $.get(config.base_server + 'personal/total?access_token=' + config.getToken(), function(data){
        console.log(data);
        var stateKindTpl = document.getElementById("state-kind").innerHTML;
        var stateKindView = document.getElementById("state-kind-box");
        laytpl(stateKindTpl).render(data.kind, function(html){
            stateKindView.innerHTML = html;
        });

        var learnLengthTpl = document.getElementById("learn-length").innerHTML;
        var learnLengthView = document.getElementById("learn-length-box");
        laytpl(learnLengthTpl).render(data.learnLength, function(html){
            learnLengthView.innerHTML = html;
        });

        var logCountTpl = document.getElementById("log-count").innerHTML;
        var logCountView = document.getElementById("log-count-box");
        laytpl(logCountTpl).render(data.logCount, function(html){
            logCountView.innerHTML = html;
        });

        var testCountTpl = document.getElementById("test-count").innerHTML;
        var testCountView = document.getElementById("test-count-box");
        laytpl(testCountTpl).render(data.testCount, function(html){
            testCountView.innerHTML = html;
        });

        var postCountTpl = document.getElementById("post-count").innerHTML;
        var postCountView = document.getElementById("post-count-box");
        laytpl(postCountTpl).render(data.postCount, function(html){
            postCountView.innerHTML = html;
        });
        element.render();
    });

    $.ajaxSettings.async = true;

    // 获取学习习惯
    $.get(config.base_server + 'personal/habit?access_token=' + config.getToken(), function(data){
        console.log(data);
        var activeTimeTpl = document.getElementById("active-time").innerHTML;
        var activeTimeView = document.getElementById("active-time-box");
        laytpl(activeTimeTpl).render(data.activeTime, function(html){
            activeTimeView.innerHTML = html;
        });

        var activeDomainTpl = document.getElementById("active-domain").innerHTML;
        var activeDomainView = document.getElementById("active-domain-box");
        laytpl(activeDomainTpl).render(data.activeDomain, function(html){
            activeDomainView.innerHTML = html;
        });

        var activeAreaTpl = document.getElementById("active-area").innerHTML;
        var activeAreaView = document.getElementById("active-area-box");
        laytpl(activeAreaTpl).render(data.activeArea, function(html){
            activeAreaView.innerHTML = html;
        });

        var activeSystemTpl = document.getElementById("active-system").innerHTML;
        var activeSystemView = document.getElementById("active-system-box");
        laytpl(activeSystemTpl).render(data.activeSystem, function(html){
            activeSystemView.innerHTML = html;
        });
        element.render();
    });

    $.get(config.base_server + 'personal/courses?access_token=' + config.getToken(), function(data) {
        var coursesTpl = document.getElementById("courses").innerHTML;
        var coursesView = document.getElementById("courses-box");
        laytpl(coursesTpl).render(data, function(html){
            coursesView.innerHTML = html;
        });
        element.render();
        form.render();
    });

    // 学习能力分析
    var myChart1 = echarts.init(document.getElementById('analyseLearnerAbility'));
    myChart1.showLoading();
    $.get(config.base_server + 'personal/ability?access_token=' + config.getToken(), function(data){
        console.log(data);
        var option = {
            backgroundColor: '#ffffff',
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                top: 30,
                bottom: 30
            },
            xAxis: {
                type: 'value',
                position: 'top',
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            },
            yAxis: {
                type: 'category',
                axisLine: {show: false},
                axisLabel: {show: false},
                axisTick: {show: false},
                splitLine: {show: false},
                data: data.courses
            },
            series: [
                {
                    name: '学习能力',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        formatter: '{b}'
                    },
                    data: data.ability
                }
            ]
        };
        myChart1.hideLoading();
        myChart1.setOption(option);
    });

    // 课程习题成绩
    var myChart0 = echarts.init(document.getElementById('getLeanerTestScore'));
    myChart0.showLoading();
    $.get(config.base_server + 'personal/score?access_token=' + config.getToken(), function(data) {
        //console.log("courses");
        //console.log(data);
        var option = {
            backgroundColor: '#ffffff',
            color: ['#4cabce', '#e5323e'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            legend: {
                left: "5%",
                data: ['full score', 'score']
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {show: false},
                    data: data.problems
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'full score',
                    type: 'bar',
                    barGap: 0,
                    data: data.fullScores
                },
                {
                    name: 'score',
                    type: 'bar',
                    data: data.scores
                }
            ]
        };
        myChart0.hideLoading();
        myChart0.setOption(option);
    });

    form.on('select(course-select)', function(data){
        $.get(config.base_server + 'personal/score?access_token=' + config.getToken() + "&courseId=" + data.value, function(data){
            console.log("courses");
            console.log(data);
            var option = {
                backgroundColor: '#ffffff',
                color: ['#4cabce', '#e5323e'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    left: "5%",
                    data: ['full score', 'score']
                },
                xAxis: [
                    {
                        type: 'category',
                        axisTick: {show: false},
                        data: data.problems
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: 'full score',
                        type: 'bar',
                        barGap: 0,
                        data: data.fullScores
                    },
                    {
                        name: 'score',
                        type: 'bar',
                        data: data.scores
                    }
                ]
            };
            myChart0.setOption(option);
        });
    });

    var myChart2 = echarts.init(document.getElementById('getLearnerVideoData'));
    myChart2.showLoading();
    // 获取视频观看情况
    $.get(config.base_server + 'personal/videoWatch?access_token=' + config.getToken(), function(data){
        var speed = [];
        for(var key in data.speed){
            speed.push({name: key, value: data.speed[key]});
        }
        var videoLen = [];
        for(var key in data.videoLen){
            videoLen.push({name: key, value: data.videoLen[key]});
        }
        console.log(speed);
        console.log(videoLen);

        option = {
            backgroundColor: '#ffffff',
            title: [
                {
                    subtext: '播放速度',
                    left: '30%',
                    top: '8%',
                    textAlign: 'center',
                    subtextStyle: {
                        fontSize: 15,
                        color: '#000000'
                    }
                },
                {
                    subtext: '视频长度',
                    left: '70%',
                    top: '8%',
                    textAlign: 'center',
                    subtextStyle: {
                        fontSize: 15,
                        color: '#000000'
                    }
                }
            ],
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            series: [
                {
                    name: '播放速度',
                    type: 'pie',
                    radius: '45%',
                    center: ['30%', '55%'],
                    data: speed
                },{
                    name: '视频长度',
                    type: 'pie',
                    radius: '45%',
                    center: ['70%', '55%'],
                    data: videoLen
                }
            ]
        };
        myChart2.hideLoading();
        myChart2.setOption(option);
    });


    var myChart7 = echarts.init(document.getElementById('getLearnerPersonality'));
    myChart7.showLoading();
    $.get(config.base_server + 'personal/personality?access_token=' + config.getToken(), function (data) {
        var personalityValue = [];
        for(var i = 0; i < data.length; i ++){
            personalityValue.push(data[i].value);
        }
        option = {
            backgroundColor: '#ffffff',
            tooltip: {},
            calculable : true,
            legend: {
                x: 'left',
                y: 'bottom',
                data: ['学习风格']
            },
            radar : [
                {
                    indicator : [
                        {text: "宜人型", max: 1},
                        {text: "严谨型", max: 1},
                        {text: "外倾型", max: 1},
                        {text: "神经质", max: 1},
                        {text: "经验开放型", max: 1}
                    ]
                }
            ],
            series: [
                {
                    type: 'radar',
                    itemStyle: {normal: {areaStyle: {type: 'default'}}},
                    data: [
                        {
                            value: personalityValue,
                            name: '学习风格'
                        }
                    ]
                }
            ]
        };
        myChart7.hideLoading();
        myChart7.setOption(option);

        data = data.sort(function (a, b) {
            return b.value - a.value;
        });
        var personalitys = [];
        for(var j = 0; j < 2; j ++){
            personalitys.push(data[j].key);
        }
        var personalityTpl = document.getElementById("personality").innerHTML;
        var personalityView = document.getElementById("personality-box");
        laytpl(personalityTpl).render(personalitys, function(html){
            personalityView.innerHTML = html;
            element.render();
        });
    });

    var myChart4 = echarts.init(document.getElementById('getLearnerActiveTime1'));
    myChart4.showLoading();
    // 获取活跃情况
    $.get(config.base_server + 'personal/active?access_token=' + config.getToken(), function(data){
        console.log(data);
        option = {
            backgroundColor: '#ffffff',
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['工作日', '休息日']
            },
            xAxis: {
                type: 'category',
                data: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00',
                    '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: '工作日',
                    type: 'line',
                    data: data.workday
                },
                {
                    name: '休息日',
                    type: 'line',
                    data: data.weekend
                }
            ]
        };
        myChart4.hideLoading();
        myChart4.setOption(option);
    });
});