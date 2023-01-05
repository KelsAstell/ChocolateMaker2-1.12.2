
package wolf.astell.choco.api;

import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class Vector3
{

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