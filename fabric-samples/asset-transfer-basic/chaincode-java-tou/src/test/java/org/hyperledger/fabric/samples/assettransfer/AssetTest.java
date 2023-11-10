package org.hyperledger.fabric.samples.assettransfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class AssetTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive() {
            Asset asset = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", LocalDateTime.now(), "IN", "UNUSED");

            assertThat(asset).isEqualTo(asset);
        }

        @Test
        public void isSymmetric() {
            LocalDateTime now = LocalDateTime.now();
            Asset assetA = new Asset("asset1", 1L, 1L, 1L, "Location1",
                    "Branch1", "Contact1", "Stock1", 100L, "Unit1",
                    now, "IN", "UNUSED");

            Asset assetB = new Asset("asset1", 1L, 1L, 1L, "Location1",
                    "Branch1", "Contact1", "Stock1", 100L, "Unit1",
                    now, "IN", "UNUSED");

            assertThat(assetA).isEqualTo(assetB);
            assertThat(assetB).isEqualTo(assetA);
        }

        @Test
        public void isTransitive() {
            LocalDateTime now = LocalDateTime.now();
            Asset assetA = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", now, "IN", "UNUSED");
            Asset assetB = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", now, "IN", "UNUSED");
            Asset assetC = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", now, "IN", "UNUSED");

            assertThat(assetA).isEqualTo(assetB);
            assertThat(assetB).isEqualTo(assetC);
            assertThat(assetA).isEqualTo(assetC);
        }

        @Test
        public void handlesInequality() {
            LocalDateTime now = LocalDateTime.now();
            Asset assetA = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", now, "IN", "UNUSED");
            Asset assetB = new Asset("asset2", 2L, 2L, 2L, "Location2", "Branch2", "Contact2", "Stock2", 200L, "Unit2", now, "OUT", "UNUSED");

            assertThat(assetA).isNotEqualTo(assetB);
        }

        @Test
        public void handlesOtherObjects() {
            Asset assetA = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", LocalDateTime.now(), "IN", "UNUSED");
            String assetB = "not an asset";

            assertThat(assetA).isNotEqualTo(assetB);
        }

        @Test
        public void handlesNull() {
            Asset asset = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", LocalDateTime.now(), "IN", "UNUSED");

            assertThat(asset).isNotEqualTo(null);
        }
    }

    @Test
    public void toStringIdentifiesAsset() {
        LocalDateTime now = LocalDateTime.now();
        Asset asset = new Asset("asset1", 1L, 1L, 1L, "Location1", "Branch1", "Contact1", "Stock1", 100L, "Unit1", now, "IN", "UNUSED");

        assertThat(asset.toString()).contains("Asset@");
        assertThat(asset.toString()).contains("assetId=asset1");
        assertThat(asset.toString()).contains("stockSeq=1");
        assertThat(asset.toString()).contains("statementSeq=1");
        assertThat(asset.toString()).contains("branchSeq=1");
        assertThat(asset.toString()).contains("branchLocation='Location1'");
        assertThat(asset.toString()).contains("branchName='Branch1'");
        assertThat(asset.toString()).contains("branchContact='Contact1'");
        assertThat(asset.toString()).contains("stockName='Stock1'");
        assertThat(asset.toString()).contains("stockQuantity=100");
        assertThat(asset.toString()).contains("stockUnit='Unit1'");
        assertThat(asset.toString()).contains("stockDate=" + now.toString());
        assertThat(asset.toString()).contains("inoutStatus='IN'");
        assertThat(asset.toString()).contains("useStatus='UNUSED'");
    }
}