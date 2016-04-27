namespace XEyesWinForm
{
    partial class OptionsDialog
    {
        /// <summary>
        /// 必要なデザイナ変数です。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 使用中のリソースをすべてクリーンアップします。
        /// </summary>
        /// <param name="disposing">マネージ リソースが破棄される場合 true、破棄されない場合は false です。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows フォーム デザイナで生成されたコード

        /// <summary>
        /// デザイナ サポートに必要なメソッドです。このメソッドの内容を
        /// コード エディタで変更しないでください。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.FlowLayoutPanel flowLayoutPanel;
            System.Windows.Forms.GroupBox groupBox;
            System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
            System.Windows.Forms.TextBox irisSizeRatioTextBox;
            System.Windows.Forms.CheckBox saveOnExitCheckBox;
            System.Windows.Forms.Button okButton;
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(OptionsDialog));
            this.irisSizeRatioTrackBar = new System.Windows.Forms.TrackBar();
            this.modelBindingSource = new System.Windows.Forms.BindingSource(this.components);
            flowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            groupBox = new System.Windows.Forms.GroupBox();
            tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            irisSizeRatioTextBox = new System.Windows.Forms.TextBox();
            saveOnExitCheckBox = new System.Windows.Forms.CheckBox();
            okButton = new System.Windows.Forms.Button();
            flowLayoutPanel.SuspendLayout();
            groupBox.SuspendLayout();
            tableLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.irisSizeRatioTrackBar)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.modelBindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // flowLayoutPanel
            // 
            flowLayoutPanel.AutoSize = true;
            flowLayoutPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            flowLayoutPanel.Controls.Add(groupBox);
            flowLayoutPanel.Controls.Add(saveOnExitCheckBox);
            flowLayoutPanel.Controls.Add(okButton);
            flowLayoutPanel.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            flowLayoutPanel.Location = new System.Drawing.Point(0, 0);
            flowLayoutPanel.Name = "flowLayoutPanel";
            flowLayoutPanel.Size = new System.Drawing.Size(195, 141);
            flowLayoutPanel.TabIndex = 0;
            flowLayoutPanel.WrapContents = false;
            // 
            // groupBox
            // 
            groupBox.AutoSize = true;
            groupBox.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            groupBox.Controls.Add(tableLayoutPanel);
            groupBox.Dock = System.Windows.Forms.DockStyle.Fill;
            groupBox.Location = new System.Drawing.Point(3, 3);
            groupBox.Name = "groupBox";
            groupBox.Size = new System.Drawing.Size(189, 84);
            groupBox.TabIndex = 0;
            groupBox.TabStop = false;
            groupBox.Text = "瞳の大きさ(&I)";
            // 
            // tableLayoutPanel
            // 
            tableLayoutPanel.AutoSize = true;
            tableLayoutPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            tableLayoutPanel.ColumnCount = 2;
            tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 80F));
            tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 20F));
            tableLayoutPanel.Controls.Add(this.irisSizeRatioTrackBar, 0, 0);
            tableLayoutPanel.Controls.Add(irisSizeRatioTextBox, 1, 0);
            tableLayoutPanel.GrowStyle = System.Windows.Forms.TableLayoutPanelGrowStyle.FixedSize;
            tableLayoutPanel.Location = new System.Drawing.Point(3, 15);
            tableLayoutPanel.Name = "tableLayoutPanel";
            tableLayoutPanel.RowCount = 1;
            tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            tableLayoutPanel.Size = new System.Drawing.Size(180, 51);
            tableLayoutPanel.TabIndex = 1;
            // 
            // irisSizeRatioTrackBar
            // 
            this.irisSizeRatioTrackBar.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.irisSizeRatioTrackBar.DataBindings.Add(new System.Windows.Forms.Binding("Value", this.modelBindingSource, "IrisSizeRatio", true, System.Windows.Forms.DataSourceUpdateMode.OnPropertyChanged));
            this.irisSizeRatioTrackBar.LargeChange = 10;
            this.irisSizeRatioTrackBar.Location = new System.Drawing.Point(3, 3);
            this.irisSizeRatioTrackBar.Maximum = 90;
            this.irisSizeRatioTrackBar.Minimum = 10;
            this.irisSizeRatioTrackBar.Name = "irisSizeRatioTrackBar";
            this.irisSizeRatioTrackBar.Size = new System.Drawing.Size(138, 45);
            this.irisSizeRatioTrackBar.SmallChange = 5;
            this.irisSizeRatioTrackBar.TabIndex = 1;
            this.irisSizeRatioTrackBar.TickFrequency = 10;
            this.irisSizeRatioTrackBar.TickStyle = System.Windows.Forms.TickStyle.Both;
            this.irisSizeRatioTrackBar.Value = 10;
            // 
            // irisSizeRatioTextBox
            // 
            irisSizeRatioTextBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            irisSizeRatioTextBox.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.modelBindingSource, "IrisSizeRatio", true, System.Windows.Forms.DataSourceUpdateMode.Never, null, "N2"));
            irisSizeRatioTextBox.Location = new System.Drawing.Point(147, 16);
            irisSizeRatioTextBox.Name = "irisSizeRatioTextBox";
            irisSizeRatioTextBox.ReadOnly = true;
            irisSizeRatioTextBox.Size = new System.Drawing.Size(30, 19);
            irisSizeRatioTextBox.TabIndex = 2;
            irisSizeRatioTextBox.TabStop = false;
            irisSizeRatioTextBox.Text = "?.??";
            // 
            // saveOnExitCheckBox
            // 
            saveOnExitCheckBox.AutoSize = true;
            saveOnExitCheckBox.DataBindings.Add(new System.Windows.Forms.Binding("Checked", this.modelBindingSource, "SaveOnExit", true, System.Windows.Forms.DataSourceUpdateMode.OnPropertyChanged));
            saveOnExitCheckBox.Location = new System.Drawing.Point(3, 93);
            saveOnExitCheckBox.Name = "saveOnExitCheckBox";
            saveOnExitCheckBox.Size = new System.Drawing.Size(185, 16);
            saveOnExitCheckBox.TabIndex = 3;
            saveOnExitCheckBox.Text = "終了時に設定をファイルへ保存(&S)";
            saveOnExitCheckBox.UseVisualStyleBackColor = true;
            // 
            // okButton
            // 
            okButton.DialogResult = System.Windows.Forms.DialogResult.OK;
            okButton.Dock = System.Windows.Forms.DockStyle.Right;
            okButton.Location = new System.Drawing.Point(117, 115);
            okButton.Name = "okButton";
            okButton.Size = new System.Drawing.Size(75, 23);
            okButton.TabIndex = 4;
            okButton.Text = "OK";
            okButton.UseVisualStyleBackColor = true;
            // 
            // modelBindingSource
            // 
            this.modelBindingSource.DataSource = typeof(XEyesWinForm.MainFormModel);
            // 
            // OptionsDialog
            // 
            this.AcceptButton = okButton;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.CancelButton = okButton;
            this.ClientSize = new System.Drawing.Size(294, 272);
            this.Controls.Add(flowLayoutPanel);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "OptionsDialog";
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            flowLayoutPanel.ResumeLayout(false);
            flowLayoutPanel.PerformLayout();
            groupBox.ResumeLayout(false);
            groupBox.PerformLayout();
            tableLayoutPanel.ResumeLayout(false);
            tableLayoutPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.irisSizeRatioTrackBar)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.modelBindingSource)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TrackBar irisSizeRatioTrackBar;
        private System.Windows.Forms.BindingSource modelBindingSource;

    }
}
