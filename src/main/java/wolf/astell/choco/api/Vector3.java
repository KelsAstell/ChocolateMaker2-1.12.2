
package wolf.astell.choco.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class Vector3
{
	public static final Vector3 ZERO = new Vector3(0, 0, 0);

	public final double x;
	public final double y;
	public final double z;

	public Vector3(double d, double d1, double d2) {
		x = d;
		y = d1;
		z = d2;
	}

	public Vector3(Vec3d vec) {
		this(vec.x, vec.y, vec.z);
	}

	public static Vector3 fromBlockPos(BlockPos pos) {
		return new Vector3(pos.getX(), pos.getY(), pos.getZ());
	}

	public static Vector3 fromTileEntity(TileEntity e) {
		return fromBlockPos(e.getPos());
	}

	public static Vector3 fromTileEntityCenter(TileEntity e) {
		return fromTileEntity(e).add(0.5);
	}

	public double dotProduct(Vector3 vec) {
		double d = vec.x * x + vec.y * y + vec.z * z;

		if(d > 1 && d < 1.00001)
			d = 1;
		else if(d < -1 && d > -1.00001)
			d = -1;
		return d;
	}

	public Vector3 add(double d, double d1, double d2) {
		return new Vector3(x + d, y + d1, z + d2);
	}

	public Vector3 add(double d) {
		return add(d, d, d);
	}

	public Vector3 multiply(double d) {
		return multiply(d, d, d);
	}

	public Vector3 multiply(double fx, double fy, double fz) {
		return new Vector3(x * fx, y * fy, z * fz);
	}

	public double mag() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double magSquared() {
		return x * x + y * y + z * z;
	}

	public Vector3 normalize() {
		double d = mag();
		if(d != 0)
			return multiply(1 / d);

		return this;
	}

	@Override
	public String toString() {
		MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
		return "Vector3(" + new BigDecimal(x, cont) + ", " +new BigDecimal(y, cont) + ", " + new BigDecimal(z, cont) + ")";
	}

	public Vector3 perpendicular() {
		if(z == 0)
			return zCrossProduct();
		return xCrossProduct();
	}

	public Vector3 xCrossProduct() {
		double d = z;
		double d1 = -y;
		return new Vector3(0, d, d1);
	}

	public Vector3 zCrossProduct() {
		double d = y;
		double d1 = -x;
		return new Vector3(d, d1, 0);
	}

	public Vector3 project(Vector3 b) {
		double l = b.magSquared();
		if(l == 0) {
			return ZERO;
		}

		double m = dotProduct(b)/l;
		return b.multiply(m);
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vector3))
			return false;

		Vector3 v = (Vector3)o;
		return x == v.x && y == v.y && z == v.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}
}