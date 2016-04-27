using System;
using System.Configuration;
using System.Diagnostics;

namespace XEyesWpf.Configuration
{
    [AttributeUsage(AttributeTargets.Property)]
    public sealed class DoubleValidatorAttribute : ConfigurationValidatorAttribute
    {
        public DoubleValidatorAttribute()
            : base()
        {
        }

        private double _minValue = double.MinValue;

        public double MinValue
        {
            get
            {
                return _minValue;
            }
            set
            {
                if (value > MaxValue)
                    throw new ArgumentOutOfRangeException("value", value,
                        string.Format("value = {0}, MaxValue = {1}",
                        value.ToString(), MaxValue.ToString()));
                _minValue = value;
            }
        }

        private double _maxValue = double.MaxValue;

        public double MaxValue
        {
            get
            {
                return _maxValue;
            }
            set
            {
                if (value < MinValue)
                    throw new ArgumentOutOfRangeException("value", value,
                        string.Format("value = {0}, MinValue = {1}",
                        value.ToString(), MinValue.ToString()));
                _maxValue = value;
            }
        }

        private bool _includeMinValue = true;

        public bool IncludeMinValue
        {
            get { return _includeMinValue; }
            set { _includeMinValue = value; }
        }

        private bool _includeMaxValue = true;

        public bool IncludeMaxValue
        {
            get { return _includeMaxValue; }
            set { _includeMaxValue = value; }
        }

        public bool ExcludeRange { get; set; }

        public override ConfigurationValidatorBase ValidatorInstance
        {
            get
            {
                return new DoubleValidator(MinValue, IncludeMinValue,
                    MaxValue, IncludeMaxValue, ExcludeRange);
            }
        }

        private class DoubleValidator : ConfigurationValidatorBase
        {
            internal DoubleValidator(double minValue, bool includeMinValue,
                double maxValue, bool includeMaxValue, bool excludeRange)
                : base()
            {
                Debug.Assert(minValue <= maxValue,
                        string.Format("minValue = {0}, maxValue = {1}",
                        minValue.ToString(), maxValue.ToString()));
                this._minValue = minValue;
                this._includeMinValue = includeMinValue;
                this._maxValue = maxValue;
                this._includeMaxValue = includeMaxValue;
                this._excludeRange = excludeRange;
            }

            private readonly double _minValue;

            private readonly bool _includeMinValue;

            private readonly double _maxValue;

            private readonly bool _includeMaxValue;

            private readonly bool _excludeRange;

            public override bool CanValidate(Type type)
            {
                return type == typeof(double);
            }

            public override void Validate(object value)
            {
                if (value == null)
                    throw new ArgumentNullException("value");

                var valueType = value.GetType();
                if (!CanValidate(valueType))
                    throw new ArgumentException(
                        string.Format("Type of value = {0}", valueType), "value");

                double dValue = (double)value;
                bool inRange = _includeMinValue ? _minValue <= dValue : _minValue < dValue;
                if (inRange)
                    inRange = _includeMaxValue ? dValue <= _maxValue : dValue < _maxValue;
                if (!(inRange ^ _excludeRange))
                    throw new ArgumentOutOfRangeException("value", value,
                        string.Format(
                        "value = {0}, MinValue = {1}, IncludeMinValue = {2}, " +
                        "MaxValue = {3}, IncludeMaxValue = {4}, ExcludeRange = {5}",
                        dValue.ToString(), _minValue.ToString(), _includeMinValue.ToString(),
                        _maxValue.ToString(), _includeMaxValue.ToString(), _excludeRange.ToString()));
            }
        }
    }
}
