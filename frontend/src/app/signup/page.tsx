"use client"

import {useState} from "react";
import Link from 'next/link';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import VisibilityIcon from '@mui/icons-material/Visibility';

export default function SignUp(){
    const [passVisible, setPassVisible] = useState(false);
    const [conPassvisible, setConPassVisible] = useState(false);

    return (
        <main className="h-full flex bg-transparent">
            {/* Decorative Glass Left (show only on medium screens and up) */}
            <div className="hidden lg:flex w-2/5 bg-black/90">
                <div className="m-auto w-[calc(100%-40px)] h-[calc(100%-40px)] rounded-4xl"></div>
            </div>

            {/* Login Section */}
            <div className="flex flex-1 justify-center items-center">
                {/* Glass Card */}
                <div className="w-full h-full lg:h-auto lg:w-[calc(100%-200px)] flex-col text-3xl p-6 bg-black/30 border-2 lg:border-white border-transparent backdrop-blur-[4px] lg:backdrop-blur-sm lg:rounded-xl lg:shadow-lg">
                    <div className={`m-auto p-8 w-full max-w-md mx-auto `}>
                        {/* Header */}
                        <div className="text-5xl text-white font-bold text-center mb-8">
                            Sign Up
                        </div>
                        {/* Form */}
                        <form className="flex flex-col items-center gap-4 w-full">
                            {/* Email */}
                            <div className="w-full flex items-center border-2 text-white border-white rounded-full p-3 bg-black/20">
                                <label htmlFor="email" className="sr-only">
                                    Email
                                </label>
                                <input
                                    id="email"
                                    className="flex-1 bg-transparent text-base sm:text-lg placeholder:rounded-full placeholder:text-center focus:outline-none text-white text-center"
                                    type="email"
                                    placeholder="Email"
                                    autoComplete="email"
                                    required
                                />
                            </div>

                            {/* Password */}
                            <div className="w-full flex items-center border-2 text-white border-white rounded-full p-3 bg-black/20 relative">
                                <label htmlFor="password" className="sr-only">
                                    Password
                                </label>
                                <input
                                    id="password"
                                    className="flex-1 bg-transparent text-base sm:text-lg placeholder:rounded-full placeholder:text-center focus:outline-none text-white text-center"
                                    type={passVisible ? "text" : "password"}
                                    placeholder="Password"
                                    autoComplete="current-password"
                                    required
                                />
                                <button
                                    type="button"
                                    onClick={() => setPassVisible(v => !v)}
                                    aria-label={passVisible ? "Hide password" : "Show password"}
                                    className="ml-2 text-white focus:outline-none"
                                    tabIndex={0}
                                >
                                    {passVisible ? (
                                        <VisibilityIcon fontSize="medium" />
                                    ) : (
                                        <VisibilityOffIcon fontSize="medium" />
                                    )}
                                </button>
                            </div>

                            {/* Confirm Password */}
                            <div className="w-full flex items-center border-2 text-white border-white rounded-full p-3 bg-black/20 relative">
                                <label htmlFor="password" className="sr-only">
                                    Confirm Password
                                </label>
                                <input
                                    id="password"
                                    className="flex-1 bg-transparent text-base sm:text-lg placeholder:rounded-full placeholder:text-center focus:outline-none text-white text-center"
                                    type={conPassvisible ? "text" : "password"}
                                    placeholder="Confirm Password"
                                    autoComplete="current-password"
                                    required
                                />
                                <button
                                    type="button"
                                    onClick={() => setConPassVisible(v => !v)}
                                    aria-label={conPassvisible ? "Hide password" : "Show password"}
                                    className="ml-2 text-white focus:outline-none"
                                    tabIndex={0}
                                >
                                    {conPassvisible ? (
                                        <VisibilityIcon fontSize="medium" />
                                    ) : (
                                        <VisibilityOffIcon fontSize="medium" />
                                    )}
                                </button>
                            </div>


                            {/* Login Button */}
                            <button
                                type="submit"
                                className="w-full bg-gray-300/30 border-2 border-gray-400 backdrop-blur-md hover:bg-gray-300/10 text-white text-lg p-2 rounded-md transition"
                            >
                                Sign Up
                            </button>
                        </form>

                        {/* Login Link */}
                        <div className="mt-6 text-white text-base text-center">
                            <span>{"Already have an account? "}</span>
                            <Link href="/login" className="underline font-semibold hover:text-gray-200">
                                Login
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
}