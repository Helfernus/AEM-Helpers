(function ($, $document) {
    "use strict";

    $document.on("dialog-ready", function () {
        var $checkbox = $(".toggle-fieldsets [type='checkbox']"),
            $fieldsetA = $(".fieldset-a"),
            $fieldsetB = $(".fieldset-b");

        function toggleFieldsets() {
            if ($checkbox.prop("checked")) {
                $fieldsetA.show();
                $fieldsetB.hide();
            } else {
                $fieldsetA.hide();
                $fieldsetB.show();
            }
        }

        // Initial toggle on dialog load
        toggleFieldsets();

        // Toggle on checkbox change
        $checkbox.on("change", toggleFieldsets);
    });
})(jQuery, jQuery(document));
