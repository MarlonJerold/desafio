jQuery.fn.check = function(options) {
   
	var opt = {
		handle : null
	};

	jQuery.extend(opt, options);

	jQuery(this).click(function() {
		var checked_status = this.checked;
		var $check = jQuery(opt.handle);

		$check.each(function() {
			this.checked = checked_status;
		});
	});
}