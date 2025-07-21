"use client";
import { useEffect, useRef } from "react";

interface VantaEffect {
  destroy(): void;
}

export default function VantaWavesBackground() {
  const vantaRef = useRef<HTMLDivElement>(null);
  const vantaEffect = useRef<VantaEffect | null>(null);

  useEffect(() => {
    let waves: ((opts: object) => VantaEffect) | undefined;
    let THREE: typeof import("three") | undefined;
    let cancelled = false;

    if (typeof window !== "undefined" && vantaRef.current) {
      Promise.all([
        import("three"),
        import("vanta/dist/vanta.waves.min.js"),
      ]).then(([threeModule, vantaWaves]) => {
        if (cancelled) return;
        THREE = threeModule;
        waves = vantaWaves.default;
        if (waves && vantaRef.current) {
          vantaEffect.current = waves({
            el: vantaRef.current,
            mouseControls: true,
            touchControls: true,
            gyroControls: false,
            minHeight: 200.0,
            minWidth: 200.0,
            scale: 1.0,
            scaleMobile: 1.0,
            THREE: THREE,
          });
        }
      });
    }

    return () => {
      cancelled = true;
      if (vantaEffect.current) {
        vantaEffect.current.destroy();
        vantaEffect.current = null;
      }
    };
  }, []);

  return (
    <div
      ref={vantaRef}
      className="fixed top-0 left-0 -z-10 w-full h-full"
      style={{ width: "100vw", height: "100vh" }}
    />
  );
}