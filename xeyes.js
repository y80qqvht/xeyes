(function() {
    function createXEyes(irisSizeRatioSelector, eyeSvgList) {
        // クライアント座標系
        var mouseX = 0;
        var mouseY = 0;
        var eyeSvgInfoList = [];

        function initialize() {
            for (var i = 0, n = eyeSvgList.length; i < n; ++i) {
                var svg = eyeSvgList[i];
                var iris = getIris(svg);
                eyeSvgInfoList.push({
                    svg: svg,
                    iris: iris
                });
            }
        }

        function getIris(element) {
            if (element.getElementsByClassName)
                return element.getElementsByClassName("iris")[0];
            else
                return element.querySelector(".iris");
        }

        function calcIrisBounds(irisSizeRatio, eyeSvgRect) {
            // クライアント座標系
            var eyeLeft = eyeSvgRect.left;
            var eyeTop = eyeSvgRect.top;
            var eyeWidth = eyeSvgRect.width;
            var eyeHeight = eyeSvgRect.height;
            var eyeRX = eyeWidth / 2.0;
            var eyeRY = eyeHeight / 2.0;

            // 目の中心から見た座標系
            var cMouseX = mouseX - eyeLeft - eyeRX;
            var cMouseY = mouseY - eyeTop - eyeRY;

            // 目の左上から見た座標系
            var irisBounds = {
                cx: mouseX - eyeLeft,
                cy: mouseY - eyeTop,
                rx: eyeRX * irisSizeRatio,
                ry: eyeRY * irisSizeRatio
            };

            if (cMouseX == 0.0) {
                var irisCYMin = irisBounds.ry;
                var irisCYMax = eyeHeight - irisBounds.ry;
                if (irisBounds.cy < irisCYMin)
                    irisBounds.cy = irisCYMin;
                else if (irisBounds.cy > irisCYMax)
                    irisBounds.cy = irisCYMax;
            } else {
                var tan = cMouseY / cMouseX;
                var a = eyeRX - irisBounds.rx;
                var b = eyeRY - irisBounds.ry;
                var tan2 = tan * tan;
                var a2 = a * a;
                var b2 = b * b;
                var cX2 = (a2 * b2) / (a2 * tan2 + b2);
                if (cX2 < cMouseX * cMouseX) {
                    var cX = Math.sqrt(cX2);
                    if (cMouseX < 0.0)
                        cX = -cX;
                    var cY2 = cX2 * tan2;
                    var cY = Math.sqrt(cY2);
                    if (cMouseY < 0.0)
                        cY = -cY;
                    irisBounds.cx = cX + eyeRX;
                    irisBounds.cy = cY + eyeRY;
                }
            }
            return irisBounds;
        }

        function setBaseLength(svgAnimatedLength, value) {
            svgAnimatedLength.baseVal.value = value;
        }

        function updateIrisLocation(irisSizeRatio, eyeSvgInfo) {
            var iris = eyeSvgInfo.iris;
            var rect = eyeSvgInfo.svg.getBoundingClientRect();
            var irisBounds = calcIrisBounds(irisSizeRatio, rect);
            setBaseLength(iris.cx, irisBounds.cx);
            setBaseLength(iris.cy, irisBounds.cy);
            setBaseLength(iris.rx, irisBounds.rx);
            setBaseLength(iris.ry, irisBounds.ry);
        }

        initialize();
        return {
            setMousePosition: function(x, y) {
                mouseX = x;
                mouseY = y;
            },

            updateEyes: function() {
                var irisSizeRatio = parseFloat(irisSizeRatioSelector.value);
                for (var i = 0, n = eyeSvgInfoList.length; i < n; ++i)
                    updateIrisLocation(irisSizeRatio, eyeSvgInfoList[i]);
            }
        };
    }

    window.addEventListener("load", function(e) {
        var xEyes = createXEyes(
            document.getElementById("IrisSizeRatioSelector"),
            [
                document.getElementById("LeftEyeSvg"),
                document.getElementById("RightEyeSvg")
            ]);

        document.addEventListener("mousemove", function(me) {
            xEyes.setMousePosition(me.clientX, me.clientY);
        }, false);

        var animationInterval = 50;
        function timerExpired() {
            xEyes.updateEyes();
            window.setTimeout(timerExpired, animationInterval);
        }
        window.setTimeout(timerExpired, animationInterval);
    }, false);
})();
