import {Geist, Geist_Mono} from "next/font/google";
import "./globals.css";
import Navbar from "@/components/Navbar";
import VantaWavesBackground from "@/components/VantaWavesBackground";

const geistSans = Geist({
    variable: "--font-geist-sans",
    subsets: ["latin"],
});

const geistMono = Geist_Mono({
    variable: "--font-geist-mono",
    subsets: ["latin"],
});

const data = {
    height: "4",
};

export default function RootLayout({children}: Readonly<{ children: React.ReactNode }>) {
    return (
        <html lang="en">
        <body className={`${geistSans.variable} ${geistMono.variable} antialiased relative overflow-x-hidden`}>
        {/* Vanta animated background as full-screen layer */}
        <VantaWavesBackground/>

        {/* Optionally, static background image below Vanta (make z-index lower) */}
        {/*
        <div
          aria-hidden="true"
          className="fixed h-screen w-screen inset-0 -z-20 bg-cover bg-center bg-no-repeat"
          style={{backgroundImage: "url('/assets/Background.png')"}}
        />
        */}

        <Navbar height={data.height.toString()} width={"full"}/>
        <div style={{height: `calc(100vh - ${data.height}rem)`}}>
            {children}
        </div>
        </body>
        </html>
    );
}
