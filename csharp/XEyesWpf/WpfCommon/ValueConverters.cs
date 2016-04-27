using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Data;
using System.Globalization;

namespace XEyesWpf.WpfCommon
{
    internal static class ValueConverters
    {
        private static IMultiValueConverter _multiplyingConverter =
            new MultiplyingConverterImpl();

        public static IMultiValueConverter MultiplyingConverter
        {
            get { return _multiplyingConverter; }
        }

        private class MultiplyingConverterImpl : IMultiValueConverter
        {
            internal MultiplyingConverterImpl()
            {
            }

            #region IMultiValueConverter メンバー

            public object Convert(
                object[] values, Type targetType, object parameter, CultureInfo culture)
            {
                if (values.Length < 2 || !(values[0] is double) | !(values[1] is double))
                    return DependencyProperty.UnsetValue;
                if (targetType != typeof(double))
                    return DependencyProperty.UnsetValue;

                var value1 = (double)values[0];
                var value2 = (double)values[1];
                return value1 * value2;
            }

            public object[] ConvertBack(
                object value, Type[] targetTypes, object parameter, CultureInfo culture)
            {
                throw new NotImplementedException();
            }

            #endregion
        }
    }
}
