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


    var myChart1 = echarts.init(document.getElementById('analyseLearnerAbility'));
    myChart1.showLoading();
    var option = {
        backgroundColor: '#ffffff',
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['课程1', '课程2', '课程3']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['10', '20', '30', '40', '50', '60', '70']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '课程1',
                type: 'line',
                stack: '总量',
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '课程2',
                type: 'line',
                stack: '总量',
                data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
                name: '课程3',
                type: 'line',
                stack: '总量',
                data: [150, 232, 201, 154, 190, 330, 410]
            }
        ]
    };
    myChart1.hideLoading();
    myChart1.setOption(option);

    var myChart0 = echarts.init(document.getElementById('getLeanerTestScore'));
    myChart0.showLoading();
    option = {
        backgroundColor: '#ffffff',
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data: ['蒸发量', '降水量', '平均温度']
        },
        xAxis: [
            {
                type: 'category',
                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '水量',
                min: 0,
                max: 250,
                interval: 50,
                axisLabel: {
                    formatter: '{value} ml'
                }
            },
            {
                type: 'value',
                name: '温度',
                min: 0,
                max: 25,
                interval: 5,
                axisLabel: {
                    formatter: '{value} °C'
                }
            }
        ],
        series: [
            {
                name: '蒸发量',
                type: 'bar',
                data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
            },
            {
                name: '降水量',
                type: 'bar',
                data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
            },
            {
                name: '平均温度',
                type: 'line',
                yAxisIndex: 1,
                data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
            }
        ]
    };
    myChart0.hideLoading();
    myChart0.setOption(option);

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
                data: ['workday', 'weekend']
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

    var myChart5 = echarts.init(document.getElementById('getLearnerActiveTime2'));
    myChart5.showLoading();
    myChart5.hideLoading();
    myChart5.setOption(option);
});