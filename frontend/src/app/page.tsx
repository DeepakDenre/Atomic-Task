'use client';

export default function Home() {
    return (
        <main className="min-h-full bg-transparent text-white flex flex-col">

            {/* Hero Section */}
            <section className="flex items-center justify-center flex-1 px-4 py-20">
                <div className="text-center">
                    <h1 className="text-4xl text-black sm:text-6xl md:text-8xl font-bold bg-gradient-to-r from-white/20 to-gray-300/20 border-2 border-white/30 p-8 sm:p-12 md:p-20 rounded-xl backdrop-blur-sm shadow-lg">
                        Atomic Task
                    </h1>
                </div>
            </section>

            {/* Info Section */}
            <section className="bg-black/60 backdrop-blur-[4px] px-4 sm:px-8 md:px-20 py-12">
                <div className="max-w-5xl mx-auto space-y-12">
                    {/* Welcome Message */}
                    <div className="text-center">
                        <h2 className="text-2xl sm:text-3xl font-bold mb-4">Welcome to Your Personal Dashboard</h2>
                        <p className="text-base sm:text-lg text-gray-300">
                            A powerful, privacy-first dashboard to manage your daily tasks, notes, calendar events, and
                            credentials — all in one place.
                        </p>
                    </div>

                    {/* Features Section */}
                    <div>
                        <h3 className="text-xl sm:text-2xl font-semibold mb-4">Features</h3>
                        <ul className="list-disc list-inside space-y-3 text-gray-300 text-sm sm:text-base">
                            <li><strong>&#10003; Task Manager:</strong> Create and manage tasks across multiple pages. Organize your workflow your way.</li>
                            <li><strong>&#9998; Notes:</strong> Take quick notes, organize them by pages, and revisit your thoughts anytime.</li>
                            <li><strong>&#128197; Calendar:</strong> Mark events by day, week, or month. Get timely notifications for what matters most.</li>
                            <li><strong>&#128274; Credential Store:</strong> Securely save and manage your login credentials. Easy to update and copy when needed.</li>
                        </ul>
                    </div>

                    {/* Tech Stack Section */}
                    <div>
                        <h3 className="text-xl sm:text-2xl font-semibold mb-4">Tech Stack</h3>
                        <ul className="list-disc list-inside text-gray-300 space-y-2 text-sm sm:text-base">
                            <li><strong>Backend:</strong> Spring Boot</li>
                            <li><strong>Frontend:</strong> Next.js</li>
                            <li><strong>Database:</strong> MySQL</li>
                            <li><strong>Deployment:</strong> Apache Tomcat on Raspberry Pi 4B via Cloudflare Tunnel</li>
                        </ul>
                    </div>

                    {/* Closing Line */}
                    <div className="text-center pt-4">
                        <p className="text-base sm:text-lg text-gray-300">
                            Start managing your digital life the smart way.
                            <br />Explore your dashboard now.
                        </p>
                    </div>
                </div>
            </section>
        </main>
    );
}
