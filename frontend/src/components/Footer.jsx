export default function Footer() {
    return (
        <footer className="w-full bg-gradient-to-r from-[#1a0033] to-[#2d0036] py-4">
            <div className="max-w-4xl mx-auto flex flex-col items-center justify-center">
                <span className="text-purple-200 font-semibold text-sm">
                    Task Manager &copy; {new Date().getFullYear()}
                </span>
                <span className="text-purple-400 text-sm mt-1">
                    Desenvolvido por<a className="text-purple-800 underline" target="_blank" href="https://www.linkedin.com/in/vinicius-patricio-b39a17272/"> Vinicius Patricio </a>
                </span>
            </div>
        </footer>
    );
}