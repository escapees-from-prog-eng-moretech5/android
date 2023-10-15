package dev.argraur.moretech.map.items

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import dev.argraur.moretech.offices.model.atm.Atm

class AtmItem(
    val atm: Atm
): ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(atm.latitude, atm.longitude)
    }

    override fun getTitle(): String {
        return atm.address
    }

    override fun getSnippet(): String {
        return atm.address
    }

    override fun getZIndex(): Float {
        return 0f
    }
}