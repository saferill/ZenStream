package com.saferill.zenstream.ui.screens.settings.index

import com.saferill.zenstream.R
import com.saferill.zenstream.ui.components.settings.SettingEntry
import com.saferill.zenstream.ui.components.settings.SettingsDestination
import com.saferill.zenstream.widget.core.FlowWidgetEntry
import com.saferill.zenstream.widget.core.FlowWidgets

internal object WidgetsIndex {
    fun entryFor(widget: FlowWidgetEntry) =
        SettingEntry(
            key = "widgets.${widget.id}",
            title = widget.label,
            summary = widget.description,
            section = R.string.settings_widgets_add_header,
            keywords = R.string.settings_keywords_widgets,
            destination = SettingsDestination.WIDGETS,
        )

    val all = FlowWidgets.catalog.map(::entryFor)
}
