package dev.argraur.moretech.map.items

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import dev.argraur.moretech.offices.model.office.Office

class OfficeItem(
    val office: Office
): ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(office.latitude, office.longitude)
    }

    override fun getTitle(): String? {
        return office.salePointName
    }

    override fun getSnippet(): String? {
        return office.address
    }

    override fun getZIndex(): Float? {
        return 0f
    }
}