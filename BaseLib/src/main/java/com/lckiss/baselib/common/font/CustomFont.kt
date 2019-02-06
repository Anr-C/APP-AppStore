package com.lckiss.baselib.common.font

import android.content.Context
import android.graphics.Typeface
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import java.util.*

class CustomFont : ITypeface {

    companion object {
        const val TTF_FILE = "iconfont.ttf"
        lateinit var typeface: Typeface
        lateinit var mChars: HashMap<String, Char>
    }


    override fun getIcon(key: String): IIcon {
        return Icon.valueOf(key)
    }

    override fun getCharacters(): HashMap<String, Char> {
            val aChars = HashMap<String, Char>()
            for (v in Icon.values()) {
                aChars[v.name] = v.character
            }
        return aChars
    }

    override fun getMappingPrefix(): String {
        return "TIL"
    }

    override fun getFontName(): String {
        return "lckiss"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun getIconCount(): Int {
        return mChars.size
    }

    override fun getIcons(): Collection<String> {
        val icons = LinkedList<String>()
        for (value in Icon.values()) {
            icons.add(value.name)
        }
        return icons
    }

    override fun getAuthor(): String {
        return "lckiss.com"
    }

    override fun getUrl(): String {
        return "http://lckiss.com/"
    }

    override fun getDescription(): String {
        return "The premium icon font for Ionic Framework."
    }

    override fun getLicense(): String {
        return "MIT Licensed"
    }

    override fun getLicenseUrl(): String {
        return "http://lckiss.com/"
    }

    override fun getTypeface(context: Context): Typeface? {
            try {
                typeface = Typeface.createFromAsset(context.assets, TTF_FILE)
            } catch (e: Exception) {
                return null
            }
        return typeface
    }

    enum class Icon private constructor(internal var character: Char) : IIcon {

        lckiss_download('\ue600'),
        lckiss_head('\ue61b'),
        lckiss_shutdown('\ue68e');

        companion object {
            // remember the typeface so we can use it later
            lateinit var iTypeface: ITypeface
        }

        override fun getFormattedName(): String {
            return "{$name}"
        }

        override fun getCharacter(): Char {
            return character
        }

        override fun getName(): String {
            return name
        }

        override fun getTypeface(): ITypeface {
                iTypeface = CustomFont()
            return iTypeface
        }


    }


}
