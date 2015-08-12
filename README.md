# Android-NumberEditText
EditText that handle number data input

# Usage

just put in your layout 

    <ru.softbalance.widgets.NumberEditText
        android:id="@+id/amount_edittext"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:selectAllOnFocus="true"
        android:textSize="@dimen/text_size_number_input"
        app:digits_after_dot="2"
        app:digits_before_dot="10"
        app:show_soft_input_on_focus="false"/>
        
... and retrieve result in code

	BigDecimal numberValue = editText.getValue();

# Integration

	repositories {
	        ...
	        maven { url "https://raw.githubusercontent.com/hyperax/Android-NumberEditText/master/maven-repo" }
	        ...
	}

	compile 'ru.softbalance.widgets:NumberEditText:1.1.2'
