"use client";

import {useEffect, useState} from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

interface NavbarProps {
    width:string;
    height:string;
}

export default function Navbar({width,height}: NavbarProps) {
    const [menuOpen, setMenuOpen] = useState(false);
    const pathname = usePathname();

    const navLinks = [
        { name: 'Home', href: '/' },
        { name: 'Tasks', href: '/tasks' },
        { name: 'Notes', href: '/notes' },
        { name: 'Calendar', href: '/calendar' },
        { name: 'Login', href: '/login' }
    ];
    return (
        <main style={{ height: `${height}rem` }} className={`w-${width} bg-no-repeat bg-transparent text-white flex flex-col sticky top-0 left-0 z-50`}>

            {/* NAVBAR */}
            <header style={{ height: `${height}rem` }} className={`w-full sticky h-[${height}rem] w-${width} top-0 z-50 backdrop-blur-lg bg-black/40 border-b-2 border-white/10`}>
                <div className="max-w-7xl mx-auto px-4 sm:px-0 lg:px-8">
                    <div className="flex justify-between items-center h-16">
                        {/* Logo */}
                        <Link href="/" className="text-xl  sm:px-6 font-extrabold tracking-wide">
                            Atomic Task
                        </Link>

                        {/* Desktop Nav */}
                        <nav className="hidden md:flex space-x-6 font-medium">
                            {navLinks.map(link => (
                                <Link
                                    key={link.name}
                                    href={link.href}
                                    className={`hover:text-gray-300 transition ${
                                        pathname === link.href ? 'text-gray-100 underline underline-offset-4' : 'text-gray-300'
                                    }`}
                                >
                                    {link.name}
                                </Link>
                            ))}
                        </nav>

                        {/* Mobile Menu Button */}
                        <div className="md:hidden p-4">
                            <button
                                onClick={() => setMenuOpen(!menuOpen)}
                                className="text-white focus:outline-none"
                                aria-label="Toggle menu"
                            >
                                <svg
                                    className="w-6 h-6"
                                    fill="none"
                                    stroke="currentColor"
                                    strokeWidth={2}
                                    viewBox="0 0 24 24"
                                >
                                    {menuOpen ? (
                                        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                                    ) : (
                                        <path strokeLinecap="round" strokeLinejoin="round" d="M4 6h16M4 12h16M4 18h16" />
                                    )}
                                </svg>
                            </button>
                        </div>
                    </div>

                    {/* Mobile Dropdown */}
                    {menuOpen && (
                        <div className="md:hidden mt-2 pb-4 space-y-2 font-medium bg-black/60 p-5">
                            {navLinks.map(link => (
                                <Link
                                    key={link.name}
                                    href={link.href}
                                    className={`block px-2 py-1 rounded hover:bg-white/10 transition ${
                                        pathname === link.href ? 'text-white font-bold' : 'text-gray-300'
                                    }`}
                                    onClick={() => setMenuOpen(false)}
                                >
                                    {link.name}
                                </Link>
                            ))}
                        </div>
                    )}
                </div>
            </header>

            {/* Remaining homepage content goes here */}
            {/* Hero, Sections, Footer, etc. */}
        </main>
    );
}
