using System.Runtime.InteropServices;
using System.Windows;

namespace XEyesWpf.WpfCommon
{
    public static class WpfUtilities
    {
        [StructLayout(LayoutKind.Sequential)]
        private struct POINT
        {
            public int X;
            public int Y;
        }

        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool GetCursorPos(out POINT lpPoint);

        /// <summary>
        /// マウスポインタの現在の位置をスクリーン座標系で返す。
        /// </summary>
        /// <returns>マウスポインタの現在の位置（スクリーン座標系）</returns>
        public static Point GetCursorPosition()
        {
            POINT position;
            if (GetCursorPos(out position))
                return new Point(position.X, position.Y);
            else
                return new Point();
        }
    }
}
