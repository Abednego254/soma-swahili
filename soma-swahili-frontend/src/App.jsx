import React, { useState, useEffect } from 'react';
import { 
  BookOpen, Calendar, DollarSign, Award, CheckCircle, XCircle, 
  User, Shield, LogOut, ArrowRight, Wallet, Activity, Upload, 
  Video, Eye, Check, AlertCircle, Plus, FileText
} from 'lucide-react';
import './App.css';

// --- INITIAL SEED DATA FOR DEMO ---
const INITIAL_TUTORS = [
  {
    id: 101,
    firstName: "Neema",
    lastName: "Mwakipesile",
    email: "neema@somaswahili.com",
    phone: "+255 754 123 456",
    highestQualification: "BA in Kiswahili, University of Dar es Salaam",
    teachingExperience: "5 years of teaching foreigners & beginners",
    bio: "Jambo! I am Neema. I specialize in teaching conversational Swahili, grammar basics, and East African cultural etiquette. Let's learn Swahili together in a fun, relaxed environment!",
    videoUrl: "https://www.youtube.com/watch?v=demo1",
    status: "APPROVED",
    role: "TUTOR"
  },
  {
    id: 102,
    firstName: "Juma",
    lastName: "Bakari",
    email: "juma@somaswahili.com",
    phone: "+254 722 987 654",
    highestQualification: "MA in African Linguistics, Kenyatta University",
    teachingExperience: "8 years lecturing and tutoring conversational language",
    bio: "Habari! I am Juma, a linguist passionate about Kiswahili Sanifu (Standard Swahili). I will help you master the complex noun class systems and sound like a native East African speaker.",
    videoUrl: "https://www.youtube.com/watch?v=demo2",
    status: "PENDING", // Initial state for Admin Verification demonstration!
    role: "TUTOR"
  },
  {
    id: 103,
    firstName: "Asha",
    lastName: "Hamisi",
    email: "asha@somaswahili.com",
    phone: "+255 688 555 444",
    highestQualification: "Diploma in Swahili Language Instruction, State University of Zanzibar",
    teachingExperience: "3 years of hosting immersive cultural & language workshops",
    bio: "Karibu! I am Asha, born and raised in Zanzibar. My lessons focus heavily on real-life dialogues, travel prep, and the beautiful Swahili coastal vocabulary. Karibu sana!",
    videoUrl: "https://www.youtube.com/watch?v=demo3",
    status: "APPROVED",
    role: "TUTOR"
  }
];

const INITIAL_COURSES = [
  {
    id: 1,
    title: "Mwanzo: Basic Swahili Greetings & Common Phrases",
    description: "Learn to introduce yourself, say hello/goodbye, construct simple daily questions, and navigate basic social situations in standard Swahili.",
    level: "BEGINNER",
    tutorId: 101,
    lessons: [
      { id: 11, title: "Greetings (Salamu)", content: "Learn greetings like Jambo, Habari, Shikamoo, Mambo, and their proper responses." },
      { id: 12, title: "Introductions (Kujitambulisha)", content: "Learn how to state your name, origin, profession, and ask others their details." },
      { id: 13, title: "Numbers & Shopping (Nambari)", content: "Counting 1-100, asking for prices (Shilingi ngapi?), and basic bargaining vocabulary." }
    ]
  },
  {
    id: 2,
    title: "Kati: Swahili Noun Classes (Ngeli za Nomino)",
    description: "Demystify standard Swahili grammar. Understand noun categories (M-WA, KI-VI, U-I) and how they govern verbs and adjectives.",
    level: "INTERMEDIATE",
    tutorId: 102,
    lessons: [
      { id: 21, title: "The M-WA Class (People)", content: "Detailed breakdown of the human noun class prefix and its verb agreement rules." },
      { id: 22, title: "The KI-VI Class (Things)", content: "Understanding objects, tools, and abstract nouns with KI-VI alignments." },
      { id: 23, title: "Subject & Object Infixes", content: "Learn how verb modifiers work to represent direct and indirect objects in standard Kiswahili." }
    ]
  },
  {
    id: 3,
    title: "Swahili Coastal Culture & Conversational Dialects",
    description: "Discover Zanzibari dialogues, typical idioms, Taarab poetic expressions, and modern Sheng (urban street Swahili) vocabulary.",
    level: "ADVANCED",
    tutorId: 103,
    lessons: [
      { id: 31, title: "Sokoni na Mitaani (In the Market)", content: "Advanced conversational idioms used on the streets and local marketplaces." },
      { id: 32, title: "Methali na Vitendawili (Proverbs & Riddles)", content: "Explore standard Swahili proverbs (e.g. 'Pole pole ndio mwendo') and their cultural application." },
      { id: 33, title: "Sheng & Urban Slang", content: "Understand the youth slang spoken in Nairobi and Dar es Salaam." }
    ]
  }
];

export default function App() {
  // --- APP STATE ---
  const [currentUser, setCurrentUser] = useState(() => {
    const saved = localStorage.getItem('soma_user');
    return saved ? JSON.parse(saved) : null;
  });

  const [tutors, setTutors] = useState(() => {
    const saved = localStorage.getItem('soma_tutors');
    return saved ? JSON.parse(saved) : INITIAL_TUTORS;
  });

  const [courses] = useState(INITIAL_COURSES);

  const [bookings, setBookings] = useState(() => {
    const saved = localStorage.getItem('soma_bookings');
    return saved ? JSON.parse(saved) : [
      {
        id: 501,
        studentId: 1,
        studentName: "John Doe",
        tutorId: 101,
        tutorName: "Neema Mwakipesile",
        scheduledDateTime: "2026-06-28T10:00",
        durationMinutes: 60,
        status: "PENDING",
        amount: 25.00
      }
    ];
  });

  const [studentWallet, setStudentWallet] = useState(() => {
    const balance = localStorage.getItem('soma_student_wallet');
    return balance ? parseFloat(balance) : 50.00; // Seed with $50.00 for demo
  });

  const [tutorWallets, setTutorWallets] = useState(() => {
    const saved = localStorage.getItem('soma_tutor_wallets');
    return saved ? JSON.parse(saved) : { 101: 75.00, 102: 0.00, 103: 120.00 };
  });

  const [transactions, setTransactions] = useState(() => {
    const saved = localStorage.getItem('soma_transactions');
    return saved ? JSON.parse(saved) : [
      { id: "TX-1001", type: "DEPOSIT", amount: 50.00, date: "2026-06-25", description: "Initial Wallet Deposit" }
    ];
  });

  const [currentTab, setCurrentTab] = useState('overview');
  const [authMode, setAuthMode] = useState('login'); // login, register
  const [authRole, setAuthRole] = useState('STUDENT'); // STUDENT, TUTOR, ADMIN
  
  // Auth Form State
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phone, setPhone] = useState('');
  
  // Tutor Specific Registration fields
  const [highestQualification, setHighestQualification] = useState('');
  const [teachingExperience, setTeachingExperience] = useState('');
  const [bio, setBio] = useState('');
  const [videoUrl, setVideoUrl] = useState('');

  // Booking Modal State
  const [selectedTutor, setSelectedTutor] = useState(null);
  const [bookingDate, setBookingDate] = useState('');
  const [bookingTime, setBookingTime] = useState('');
  const [bookingDuration, setBookingDuration] = useState(60);
  const [bookingPrice, setBookingPrice] = useState(25.00);

  // Wallet Modal
  const [depositAmount, setDepositAmount] = useState('');
  const [showDepositModal, setShowDepositModal] = useState(false);

  // Alert/Notification State
  const [notification, setNotification] = useState(null);

  // --- PERSISTENCE WRITING ---
  useEffect(() => {
    localStorage.setItem('soma_tutors', JSON.stringify(tutors));
  }, [tutors]);

  useEffect(() => {
    localStorage.setItem('soma_bookings', JSON.stringify(bookings));
  }, [bookings]);

  useEffect(() => {
    localStorage.setItem('soma_student_wallet', studentWallet.toString());
  }, [studentWallet]);

  useEffect(() => {
    localStorage.setItem('soma_tutor_wallets', JSON.stringify(tutorWallets));
  }, [tutorWallets]);

  useEffect(() => {
    localStorage.setItem('soma_transactions', JSON.stringify(transactions));
  }, [transactions]);

  // Helper trigger notification
  const triggerNotification = (message, type = 'info') => {
    setNotification({ message, type });
    setTimeout(() => setNotification(null), 4000);
  };

  // --- ACTIONS & API SIMULATION ---
  const handleLogin = (e) => {
    e.preventDefault();
    if (!email || !password) {
      triggerNotification("Please fill in all credentials", "error");
      return;
    }

    // Simulate authentication checks
    if (authRole === 'ADMIN') {
      if (email === 'admin@somaswahili.com' && password === 'admin123') {
        const adminUser = { id: 99, firstName: "Mkuu", lastName: "Admin", email, role: 'ADMIN' };
        setCurrentUser(adminUser);
        localStorage.setItem('soma_user', JSON.stringify(adminUser));
        setCurrentTab('overview');
        triggerNotification("Jambo Admin! Access Granted.", "success");
      } else {
        triggerNotification("Invalid Admin credentials (Hint: admin@somaswahili.com / admin123)", "error");
      }
    } else if (authRole === 'TUTOR') {
      const foundTutor = tutors.find(t => t.email.toLowerCase() === email.toLowerCase());
      if (foundTutor) {
        setCurrentUser(foundTutor);
        localStorage.setItem('soma_user', JSON.stringify(foundTutor));
        setCurrentTab('overview');
        triggerNotification(`Karibu tena, Mwalimu ${foundTutor.firstName}!`, "success");
      } else {
        triggerNotification("Tutor profile not found. Please register first.", "error");
      }
    } else {
      // Student
      const studentUser = { id: 1, firstName: "John", lastName: "Doe", email, role: 'STUDENT' };
      setCurrentUser(studentUser);
      localStorage.setItem('soma_user', JSON.stringify(studentUser));
      setCurrentTab('overview');
      triggerNotification("Karibu! Logged in successfully.", "success");
    }
  };

  const handleRegister = (e) => {
    e.preventDefault();
    if (!email || !password || !firstName || !lastName || !phone) {
      triggerNotification("Please fill in all fields", "error");
      return;
    }

    if (authRole === 'TUTOR') {
      if (!highestQualification || !teachingExperience || !videoUrl) {
        triggerNotification("Tutors must provide teaching credentials and demo video", "error");
        return;
      }
      const newTutor = {
        id: Date.now(),
        firstName,
        lastName,
        email,
        phone,
        highestQualification,
        teachingExperience,
        bio,
        videoUrl,
        status: "PENDING", // Awaiting Admin Approval
        role: "TUTOR"
      };
      const updatedTutors = [...tutors, newTutor];
      setTutors(updatedTutors);
      
      // Initialize tutor wallet
      const updatedWallets = { ...tutorWallets, [newTutor.id]: 0.00 };
      setTutorWallets(updatedWallets);

      triggerNotification("Registration complete! Your profile is PENDING admin approval.", "success");
      setAuthMode('login');
    } else {
      // Student
      const newStudent = { id: Date.now(), firstName, lastName, email, role: 'STUDENT' };
      setCurrentUser(newStudent);
      localStorage.setItem('soma_user', JSON.stringify(newStudent));
      setStudentWallet(0.00);
      setCurrentTab('overview');
      triggerNotification("Karibu! Student account created.", "success");
    }
  };

  const handleLogout = () => {
    setCurrentUser(null);
    localStorage.removeItem('soma_user');
    triggerNotification("Logged out successfully. Kwaheri!", "info");
  };

  // Student: Deposit into Wallet
  const handleDeposit = (e) => {
    e.preventDefault();
    const amount = parseFloat(depositAmount);
    if (isNaN(amount) || amount <= 0) {
      triggerNotification("Please enter a valid amount to deposit", "error");
      return;
    }

    setStudentWallet(prev => prev + amount);
    const txId = "TX-" + Math.floor(1000 + Math.random() * 9000);
    const newTx = {
      id: txId,
      type: "DEPOSIT",
      amount: amount,
      date: new Date().toISOString().split('T')[0],
      description: "Wallet Refill (Simulated Gateway)"
    };
    setTransactions([newTx, ...transactions]);
    setShowDepositModal(false);
    setDepositAmount('');
    triggerNotification(`Successfully deposited $${amount.toFixed(2)} into your wallet!`, "success");
  };

  // Student: Book Session (Wallet Check)
  const handleCreateBooking = (e) => {
    e.preventDefault();
    if (!bookingDate || !bookingTime) {
      triggerNotification("Please select a date and time", "error");
      return;
    }

    if (studentWallet < bookingPrice) {
      triggerNotification(`Booking failed: Insufficient balance. You need at least $${bookingPrice.toFixed(2)} in your wallet.`, "error");
      return;
    }

    // Deduct from wallet
    setStudentWallet(prev => prev - bookingPrice);
    
    // Add debit transaction
    const txId = "TX-" + Math.floor(1000 + Math.random() * 9000);
    const debitTx = {
      id: txId,
      type: "LESSON_PAYMENT",
      amount: bookingPrice,
      date: new Date().toISOString().split('T')[0],
      description: `Held payment for booking with Mwalimu ${selectedTutor.firstName}`
    };
    setTransactions([debitTx, ...transactions]);

    // Create the booking record
    const newBooking = {
      id: Date.now(),
      studentId: currentUser.id,
      studentName: `${currentUser.firstName} ${currentUser.lastName}`,
      tutorId: selectedTutor.id,
      tutorName: `${selectedTutor.firstName} ${selectedTutor.lastName}`,
      scheduledDateTime: `${bookingDate}T${bookingTime}`,
      durationMinutes: parseInt(bookingDuration),
      status: "PENDING",
      amount: bookingPrice
    };

    setBookings([newBooking, ...bookings]);
    setSelectedTutor(null);
    triggerNotification("Booking submitted! Funds have been secured in escrow. Awaiting tutor acceptance.", "success");
  };

  // Tutor: Accept Booking request
  const handleAcceptBooking = (bookingId) => {
    const updatedBookings = bookings.map(b => {
      if (b.id === bookingId) {
        return { ...b, status: 'CONFIRMED' };
      }
      return b;
    });
    setBookings(updatedBookings);
    triggerNotification("Booking Accepted! Get ready for your lesson.", "success");
  };

  // Tutor: Decline Booking (Refunds Student)
  const handleDeclineBooking = (bookingId) => {
    const bookingToDecline = bookings.find(b => b.id === bookingId);
    if (!bookingToDecline) return;

    const updatedBookings = bookings.map(b => {
      if (b.id === bookingId) {
        return { ...b, status: 'DECLINED_BY_TUTOR' };
      }
      return b;
    });
    setBookings(updatedBookings);

    // Refund Student Wallet
    if (bookingToDecline.studentId === 1) { // Current demo student
      setStudentWallet(prev => prev + bookingToDecline.amount);
    }
    
    // Log Refund Transaction
    const txId = "TX-" + Math.floor(1000 + Math.random() * 9000);
    const refundTx = {
      id: txId,
      type: "CREDIT_BACK",
      amount: bookingToDecline.amount,
      date: new Date().toISOString().split('T')[0],
      description: `Refund for booking declined by Mwalimu ${bookingToDecline.tutorName}`
    };
    setTransactions([refundTx, ...transactions]);
    triggerNotification("Booking request declined. Student wallet has been fully refunded.", "info");
  };

  // Tutor/Admin: Complete Booking (Payout release)
  const handleCompleteBooking = (bookingId) => {
    const completedBooking = bookings.find(b => b.id === bookingId);
    if (!completedBooking) return;

    const updatedBookings = bookings.map(b => {
      if (b.id === bookingId) {
        return { ...b, status: 'COMPLETED' };
      }
      return b;
    });
    setBookings(updatedBookings);

    // Tutor Wallet Payout (90% to tutor, 10% platform commission fee)
    const tutorPay = completedBooking.amount * 0.90;
    setTutorWallets(prev => ({
      ...prev,
      [completedBooking.tutorId]: (prev[completedBooking.tutorId] || 0) + tutorPay
    }));

    triggerNotification(`Lesson Completed! 90% payout ($${tutorPay.toFixed(2)}) credited to Tutor Wallet.`, "success");
  };

  // Admin: Approve Tutor
  const handleApproveTutor = (tutorId) => {
    const updatedTutors = tutors.map(t => {
      if (t.id === tutorId) {
        return { ...t, status: 'APPROVED' };
      }
      return t;
    });
    setTutors(updatedTutors);
    triggerNotification("Tutor credentials verified and status changed to APPROVED!", "success");
  };

  // Admin: Reject Tutor
  const handleRejectTutor = (tutorId) => {
    const updatedTutors = tutors.map(t => {
      if (t.id === tutorId) {
        return { ...t, status: 'REJECTED' };
      }
      return t;
    });
    setTutors(updatedTutors);
    triggerNotification("Tutor status changed to REJECTED.", "warning");
  };

  // --- SUB-PANEL COMPONENT RENDERING ---

  // 1. Student Dashboard Screen
  const renderStudentDashboard = () => {
    const myBookings = bookings.filter(b => b.studentId === currentUser.id);

    return (
      <div className="dashboard-layout animate-fade-in">
        {/* Navigation Sidebar */}
        <aside className="sidebar glass-panel">
          <div className="brand">
            <img src="/logo.png" alt="Soma Swahili Logo" className="brand-logo" />
            <span>Soma Swahili</span>
          </div>
          <div className="user-profile-summary">
            <div className="avatar">
              <User size={20} />
            </div>
            <div>
              <p className="user-name">{currentUser.firstName}</p>
              <p className="user-role-badge">Mwanafunzi (Student)</p>
            </div>
          </div>
          <nav className="nav-menu">
            <button className={`nav-link ${currentTab === 'overview' ? 'active' : ''}`} onClick={() => setCurrentTab('overview')}>
              <Activity size={18} /> Overview
            </button>
            <button className={`nav-link ${currentTab === 'tutors' ? 'active' : ''}`} onClick={() => setCurrentTab('tutors')}>
              <Award size={18} /> Explore Tutors
            </button>
            <button className={`nav-link ${currentTab === 'bookings' ? 'active' : ''}`} onClick={() => setCurrentTab('bookings')}>
              <Calendar size={18} /> My Bookings ({myBookings.length})
            </button>
            <button className={`nav-link ${currentTab === 'wallet' ? 'active' : ''}`} onClick={() => setCurrentTab('wallet')}>
              <Wallet size={18} /> Wallet & Funds
            </button>
          </nav>
          <button className="logout-btn" onClick={handleLogout}>
            <LogOut size={18} /> Toka (Logout)
          </button>
        </aside>

        {/* Main Content Area */}
        <main className="dashboard-content">
          {currentTab === 'overview' && (
            <div className="tab-pane">
              <div className="dashboard-header">
                <h1>Jambo, {currentUser.firstName}! 👋</h1>
                <p>Welcome back to your Kiswahili learning center. Let's make progress today!</p>
              </div>

              {/* Stats Grid */}
              <div className="stats-grid">
                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Wallet Balance</span>
                    <Wallet className="stat-icon icon-gold" />
                  </div>
                  <h2 className="stat-value">${studentWallet.toFixed(2)}</h2>
                  <button className="btn-primary btn-sm mt-3" onClick={() => setShowDepositModal(true)}>
                    <Plus size={16} /> Add Funds
                  </button>
                </div>

                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Active Bookings</span>
                    <Calendar className="stat-icon icon-blue" />
                  </div>
                  <h2 className="stat-value">
                    {myBookings.filter(b => b.status === 'PENDING' || b.status === 'CONFIRMED').length}
                  </h2>
                  <p className="stat-subtitle">Awaiting lesson delivery</p>
                </div>

                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Lessons Completed</span>
                    <CheckCircle className="stat-icon icon-green" />
                  </div>
                  <h2 className="stat-value">
                    {myBookings.filter(b => b.status === 'COMPLETED').length}
                  </h2>
                  <p className="stat-subtitle">Hongera! Keep studying</p>
                </div>
              </div>

              {/* Quick actions or featured tutors */}
              <div className="grid-2 mt-5">
                <div className="panel glass-panel">
                  <h3>Browse Featured Tutors</h3>
                  <p className="mb-4">Select an approved native speaker to begin studying 1-on-1.</p>
                  <div className="tutor-mini-list">
                    {tutors.filter(t => t.status === 'APPROVED').slice(0, 2).map(t => (
                      <div key={t.id} className="tutor-mini-item glass-card p-3 mb-2 flex justify-between align-center">
                        <div>
                          <strong>Mwalimu {t.firstName} {t.lastName}</strong>
                          <p className="text-secondary text-sm">{t.highestQualification}</p>
                        </div>
                        <button className="btn-secondary btn-sm" onClick={() => { setSelectedTutor(t); setCurrentTab('tutors'); }}>
                          View Profile
                        </button>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="panel glass-panel">
                  <h3>Recent Wallet Transactions</h3>
                  <div className="tx-table mt-3">
                    {transactions.slice(0, 3).map(tx => (
                      <div key={tx.id} className="tx-row flex justify-between align-center py-2 border-b">
                        <div>
                          <p className="tx-desc font-medium">{tx.description}</p>
                          <span className="tx-date text-xs text-secondary">{tx.date}</span>
                        </div>
                        <span className={`tx-amt font-bold ${tx.type === 'DEPOSIT' || tx.type === 'CREDIT_BACK' ? 'text-green' : 'text-danger'}`}>
                          {tx.type === 'DEPOSIT' || tx.type === 'CREDIT_BACK' ? '+' : '-'}${tx.amount.toFixed(2)}
                        </span>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}

          {currentTab === 'tutors' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>Find Swahili Tutors</h1>
                <p>Browse through our verified Swahili teachers, view their qualifications, courses, and book a lesson.</p>
              </div>

              <div className="tutors-grid">
                {tutors.filter(t => t.status === 'APPROVED').map(t => {
                  const tutorCourses = courses.filter(c => c.tutorId === t.id);
                  return (
                    <div key={t.id} className="tutor-full-card glass-panel p-4 mb-4">
                      <div className="tutor-header-row flex justify-between align-start">
                        <div className="flex align-center gap-3">
                          <div className="tutor-avatar">
                            <User size={32} />
                          </div>
                          <div>
                            <h2>Mwalimu {t.firstName} {t.lastName}</h2>
                            <span className="badge badge-success flex align-center gap-1 w-fit">
                              <CheckCircle size={12} /> Verified Native Speaker
                            </span>
                          </div>
                        </div>
                        <button className="btn-primary" onClick={() => setSelectedTutor(t)}>
                          Book Lesson ($25.00/hr)
                        </button>
                      </div>

                      <div className="tutor-body mt-4">
                        <div className="grid-2-1">
                          <div>
                            <h4 className="font-semibold text-gold">Bio</h4>
                            <p className="text-secondary mb-3">{t.bio}</p>

                            <h4 className="font-semibold text-gold">Qualifications & Experience</h4>
                            <ul className="qualifications-list text-secondary text-sm">
                              <li>🎓 <strong>Education:</strong> {t.highestQualification}</li>
                              <li>💼 <strong>Experience:</strong> {t.teachingExperience}</li>
                            </ul>
                          </div>

                          <div className="video-preview-panel glass-card p-3 flex flex-column justify-center align-center">
                            <Video className="text-gold mb-2" size={32} />
                            <p className="text-sm font-semibold">Intro Video Available</p>
                            <a href={t.videoUrl} target="_blank" rel="noreferrer" className="text-xs text-blue underline mt-1">
                              Watch Demonstration
                            </a>
                          </div>
                        </div>

                        {/* Course syllabus attachment */}
                        <div className="tutor-courses-section mt-4 pt-3 border-t">
                          <h3 className="text-md mb-2 flex align-center gap-2">
                            <BookOpen size={18} className="icon-blue" /> Course Curriculums Offered:
                          </h3>
                          <div className="courses-syllabus-grid">
                            {tutorCourses.map(c => (
                              <div key={c.id} className="syllabus-card glass-card p-3">
                                <span className="level-badge">{c.level}</span>
                                <h4 className="font-semibold mt-2">{c.title}</h4>
                                <p className="text-xs text-secondary mb-2">{c.description}</p>
                                <div className="lessons-mini-syllabus">
                                  <p className="text-xs font-semibold text-gold mb-1">Syllabus Lessons:</p>
                                  {c.lessons.map((lesson, idx) => (
                                    <div key={lesson.id} className="lesson-mini-title flex align-center gap-1 text-xs text-secondary">
                                      <Check size={12} className="text-green" /> Lesson {idx + 1}: {lesson.title}
                                    </div>
                                  ))}
                                </div>
                              </div>
                            ))}
                          </div>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          )}

          {currentTab === 'bookings' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>My Booking History</h1>
                <p>Track your scheduled sessions, check tutor approvals, and mark lessons as complete.</p>
              </div>

              {myBookings.length === 0 ? (
                <div className="glass-panel p-5 text-center">
                  <Calendar className="text-muted mb-2" size={48} />
                  <p className="text-secondary">No scheduled bookings found. Go to "Explore Tutors" to select a guide.</p>
                </div>
              ) : (
                <div className="booking-list">
                  {myBookings.map(b => (
                    <div key={b.id} className="booking-card glass-panel p-4 mb-3 flex justify-between align-center">
                      <div>
                        <h3>Swahili Lesson with Mwalimu {b.tutorName}</h3>
                        <p className="text-secondary text-sm">
                          📅 {new Date(b.scheduledDateTime).toLocaleString()} ({b.durationMinutes} minutes)
                        </p>
                        <p className="text-xs text-muted mt-1">Escrow Fee: ${b.amount.toFixed(2)}</p>
                      </div>

                      <div className="flex align-center gap-3">
                        <span className={`badge badge-${b.status.toLowerCase()}`}>
                          {b.status}
                        </span>

                        {b.status === 'CONFIRMED' && (
                          <button className="btn-primary btn-sm" onClick={() => handleCompleteBooking(b.id)}>
                            Mark Completed
                          </button>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {currentTab === 'wallet' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>Wallet & Financial Dashboard</h1>
                <p>Ensure you have a valid balance. Payments are locked in escrow when booking and released to tutors upon lesson completion.</p>
              </div>

              <div className="grid-2-1">
                <div className="glass-panel p-4">
                  <h3>Transaction History</h3>
                  <div className="tx-list mt-3">
                    {transactions.map(tx => (
                      <div key={tx.id} className="tx-item glass-card p-3 mb-2 flex justify-between align-center">
                        <div>
                          <div className="flex align-center gap-2">
                            <span className={`tx-badge badge-${tx.type.toLowerCase()}`}>
                              {tx.type}
                            </span>
                            <span className="font-semibold text-sm">{tx.description}</span>
                          </div>
                          <span className="text-xs text-muted d-block mt-1">{tx.date} • Reference ID: {tx.id}</span>
                        </div>
                        <span className={`font-bold ${tx.type === 'DEPOSIT' || tx.type === 'CREDIT_BACK' ? 'text-green' : 'text-danger'}`}>
                          {tx.type === 'DEPOSIT' || tx.type === 'CREDIT_BACK' ? '+' : '-'}${tx.amount.toFixed(2)}
                        </span>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="glass-panel p-4 flex flex-column justify-between h-fit">
                  <div>
                    <h3 className="mb-2">My Account Balance</h3>
                    <div className="balance-display flex align-center gap-2 mb-4">
                      <Wallet size={32} className="text-gold" />
                      <h1 className="text-3xl">${studentWallet.toFixed(2)}</h1>
                    </div>
                    <p className="text-xs text-secondary mb-4">
                      Top up your wallet using mobile money (M-Pesa, Airtel Money) or Credit Card.
                    </p>
                  </div>
                  <button className="btn-primary w-full justify-center" onClick={() => setShowDepositModal(true)}>
                    Deposit Funds
                  </button>
                </div>
              </div>
            </div>
          )}
        </main>
      </div>
    );
  };

  // 2. Tutor Dashboard Screen
  const renderTutorDashboard = () => {
    const myBookings = bookings.filter(b => b.tutorId === currentUser.id);
    const pendingBookings = myBookings.filter(b => b.status === 'PENDING');
    const confirmedBookings = myBookings.filter(b => b.status === 'CONFIRMED');
    const myWallet = tutorWallets[currentUser.id] || 0.00;

    return (
      <div className="dashboard-layout animate-fade-in">
        {/* Navigation Sidebar */}
        <aside className="sidebar glass-panel">
          <div className="brand">
            <img src="/logo.png" alt="Soma Swahili Logo" className="brand-logo" />
            <span>Soma Swahili</span>
          </div>
          <div className="user-profile-summary">
            <div className="avatar">
              <User size={20} />
            </div>
            <div>
              <p className="user-name">Mwalimu {currentUser.firstName}</p>
              <p className="user-role-badge">Tutor Portal</p>
            </div>
          </div>
          <nav className="nav-menu">
            <button className={`nav-link ${currentTab === 'overview' ? 'active' : ''}`} onClick={() => setCurrentTab('overview')}>
              <Activity size={18} /> Overview
            </button>
            <button className={`nav-link ${currentTab === 'requests' ? 'active' : ''}`} onClick={() => setCurrentTab('requests')}>
              <Calendar size={18} /> Booking Requests ({pendingBookings.length})
            </button>
            <button className={`nav-link ${currentTab === 'wallet' ? 'active' : ''}`} onClick={() => setCurrentTab('wallet')}>
              <DollarSign size={18} /> My Earnings (${myWallet.toFixed(2)})
            </button>
            <button className={`nav-link ${currentTab === 'documents' ? 'active' : ''}`} onClick={() => setCurrentTab('documents')}>
              <Upload size={18} /> Document Center
            </button>
          </nav>
          <button className="logout-btn" onClick={handleLogout}>
            <LogOut size={18} /> Toka (Logout)
          </button>
        </aside>

        {/* Main Content Area */}
        <main className="dashboard-content">
          {currentTab === 'overview' && (
            <div className="tab-pane">
              <div className="dashboard-header">
                <h1>Jambo, Mwalimu {currentUser.firstName}! 🎓</h1>
                <p>Manage your Swahili classes, review incoming lesson requests, and request payouts.</p>
              </div>

              {/* Status Alert for verification */}
              {currentUser.status === 'PENDING' && (
                <div className="alert alert-warning flex align-center gap-3 p-3 mb-4 rounded border-warning">
                  <AlertCircle className="text-warning" />
                  <div>
                    <strong className="text-warning">Profile Verification Pending</strong>
                    <p className="text-secondary text-sm">
                      An Admin supervisor is reviewing your credentials. You will be able to receive student bookings once your profile status is set to APPROVED.
                    </p>
                  </div>
                </div>
              )}

              {/* Stats Grid */}
              <div className="stats-grid">
                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Total Earnings</span>
                    <DollarSign className="stat-icon icon-gold" />
                  </div>
                  <h2 className="stat-value">${myWallet.toFixed(2)}</h2>
                  <p className="stat-subtitle">Available for payout withdrawal</p>
                </div>

                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Pending Bookings</span>
                    <Calendar className="stat-icon icon-blue" />
                  </div>
                  <h2 className="stat-value">{pendingBookings.length}</h2>
                  <p className="stat-subtitle">Awaiting your response</p>
                </div>

                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Upcoming Lessons</span>
                    <CheckCircle className="stat-icon icon-green" />
                  </div>
                  <h2 className="stat-value">{confirmedBookings.length}</h2>
                  <p className="stat-subtitle">Tutor sessions confirmed</p>
                </div>
              </div>

              {/* Dashboard lists */}
              <div className="grid-2 mt-5">
                <div className="panel glass-panel">
                  <h3>Recent Booking Requests</h3>
                  <div className="mt-3">
                    {pendingBookings.length === 0 ? (
                      <p className="text-secondary text-sm">No new requests pending. Keep your profile updated!</p>
                    ) : (
                      pendingBookings.map(b => (
                        <div key={b.id} className="booking-req-item glass-card p-3 mb-2 flex justify-between align-center">
                          <div>
                            <strong>Student: {b.studentName}</strong>
                            <p className="text-xs text-secondary">{new Date(b.scheduledDateTime).toLocaleString()}</p>
                          </div>
                          <div className="flex gap-2">
                            <button className="btn-primary btn-sm" onClick={() => handleAcceptBooking(b.id)}>
                              Accept
                            </button>
                            <button className="btn-secondary btn-sm" onClick={() => handleDeclineBooking(b.id)}>
                              Decline
                            </button>
                          </div>
                        </div>
                      ))
                    )}
                  </div>
                </div>

                <div className="panel glass-panel">
                  <h3>Tutor Profile Details</h3>
                  <ul className="tutor-profile-details text-sm mt-3 text-secondary">
                    <li className="mb-2"><strong>Role:</strong> Swahili language tutor</li>
                    <li className="mb-2"><strong>Verification Status:</strong> 
                      <span className={`badge badge-${currentUser.status.toLowerCase()} ml-2`}>
                        {currentUser.status}
                      </span>
                    </li>
                    <li className="mb-2"><strong>Qualifications:</strong> {currentUser.highestQualification}</li>
                    <li className="mb-2"><strong>Experience:</strong> {currentUser.teachingExperience}</li>
                  </ul>
                </div>
              </div>
            </div>
          )}

          {currentTab === 'requests' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>Student Booking Requests</h1>
                <p>Accept bookings to schedule classes, or decline them to release student wallet escrow locks.</p>
              </div>

              {pendingBookings.length === 0 ? (
                <div className="glass-panel p-5 text-center">
                  <Calendar className="text-muted mb-2" size={48} />
                  <p className="text-secondary">No pending booking requests at this time.</p>
                </div>
              ) : (
                <div className="booking-requests-list">
                  {pendingBookings.map(b => (
                    <div key={b.id} className="booking-card glass-panel p-4 mb-3 flex justify-between align-center">
                      <div>
                        <h3>Swahili Lesson request from {b.studentName}</h3>
                        <p className="text-secondary text-sm">
                          📅 {new Date(b.scheduledDateTime).toLocaleString()} ({b.durationMinutes} minutes)
                        </p>
                        <p className="text-xs text-muted mt-1">Student Locked Escrow: ${b.amount.toFixed(2)}</p>
                      </div>

                      <div className="flex gap-2">
                        <button className="btn-primary" onClick={() => handleAcceptBooking(b.id)}>
                          Accept & Schedule
                        </button>
                        <button className="btn-secondary" onClick={() => handleDeclineBooking(b.id)}>
                          Decline Request
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {currentTab === 'wallet' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>Tutor Earnings Wallet</h1>
                <p>Earned balances are calculated at 90% of the session fee upon student-marked lesson completion.</p>
              </div>

              <div className="grid-2-1">
                <div className="glass-panel p-4">
                  <h3>Payout History</h3>
                  <div className="tx-list mt-3">
                    <p className="text-secondary text-sm text-center py-4">No recent payouts completed yet.</p>
                  </div>
                </div>

                <div className="glass-panel p-4 flex flex-column justify-between h-fit">
                  <div>
                    <h3 className="mb-2">Available Balance</h3>
                    <div className="balance-display flex align-center gap-2 mb-4">
                      <DollarSign size={32} className="text-gold" />
                      <h1 className="text-3xl">${myWallet.toFixed(2)}</h1>
                    </div>
                    <p className="text-xs text-secondary mb-4">
                      Platform fee is 10% on lesson fees. Payout requests are processed within 24 hours.
                    </p>
                  </div>
                  <button className="btn-primary w-full justify-center" disabled={myWallet <= 0} onClick={() => triggerNotification("Payout request submitted successfully!", "success")}>
                    Request Payout
                  </button>
                </div>
              </div>
            </div>
          )}

          {currentTab === 'documents' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>Tutor Certification Center</h1>
                <p>Upload files (Degree, Diploma, Language certificates) for admin review. Verification unlocks booking eligibility.</p>
              </div>

              <div className="glass-panel p-4">
                <h3>Uploaded Credentials</h3>
                <div className="mt-3 flex gap-3 flex-wrap">
                  <div className="document-badge glass-card p-3 flex align-center gap-2">
                    <FileText className="text-gold" />
                    <div>
                      <p className="text-sm font-semibold">UDSM_BA_Kiswahili.pdf</p>
                      <span className="text-xs text-green">Verified</span>
                    </div>
                  </div>

                  <div className="document-badge glass-card p-3 flex align-center gap-2">
                    <FileText className="text-gold" />
                    <div>
                      <p className="text-sm font-semibold">Tanzania_Teacher_Reg.pdf</p>
                      <span className="text-xs text-green">Verified</span>
                    </div>
                  </div>
                </div>

                <div className="file-uploader-box mt-5 p-5 border-dashed rounded text-center glass-card">
                  <Upload size={32} className="text-muted mb-2" />
                  <p className="font-semibold text-sm">Drag and drop files here to upload credentials</p>
                  <p className="text-xs text-secondary">PDF, JPG, PNG (Max 5MB)</p>
                  <button className="btn-secondary btn-sm mt-3" onClick={() => triggerNotification("Document upload simulated successfully!", "success")}>
                    Choose File
                  </button>
                </div>
              </div>
            </div>
          )}
        </main>
      </div>
    );
  };

  // 3. Admin Dashboard Screen (Supervisor)
  const renderAdminDashboard = () => {
    const pendingTutorsList = tutors.filter(t => t.status === 'PENDING');
    const allBookings = bookings;
    
    // Compute total volume
    const totalVolume = bookings.reduce((sum, b) => sum + b.amount, 0);
    const commissionCollected = totalVolume * 0.10;

    return (
      <div className="dashboard-layout animate-fade-in">
        {/* Navigation Sidebar */}
        <aside className="sidebar glass-panel">
          <div className="brand">
            <img src="/logo.png" alt="Soma Swahili Logo" className="brand-logo" />
            <span>Soma Swahili</span>
          </div>
          <div className="user-profile-summary">
            <div className="avatar">
              <Shield size={20} />
            </div>
            <div>
              <p className="user-name">Supervisor</p>
              <p className="user-role-badge">Admin System</p>
            </div>
          </div>
          <nav className="nav-menu">
            <button className={`nav-link ${currentTab === 'overview' ? 'active' : ''}`} onClick={() => setCurrentTab('overview')}>
              <Activity size={18} /> Platform Analytics
            </button>
            <button className={`nav-link ${currentTab === 'verification' ? 'active' : ''}`} onClick={() => setCurrentTab('verification')}>
              <Shield size={18} /> Tutor Approvals ({pendingTutorsList.length})
            </button>
            <button className={`nav-link ${currentTab === 'all-tutors' ? 'active' : ''}`} onClick={() => setCurrentTab('all-tutors')}>
              <Award size={18} /> Manage All Tutors
            </button>
          </nav>
          <button className="logout-btn" onClick={handleLogout}>
            <LogOut size={18} /> Toka (Logout)
          </button>
        </aside>

        {/* Main Content Area */}
        <main className="dashboard-content">
          {currentTab === 'overview' && (
            <div className="tab-pane">
              <div className="dashboard-header">
                <h1>Platform Supervision Dashboard</h1>
                <p>Overview of system metrics, active transactions, and platform verification statistics.</p>
              </div>

              {/* Stats Grid */}
              <div className="stats-grid">
                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Platform Sales Volume</span>
                    <DollarSign className="stat-icon icon-blue" />
                  </div>
                  <h2 className="stat-value">${totalVolume.toFixed(2)}</h2>
                  <p className="stat-subtitle">Deducted from student wallets</p>
                </div>

                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Service Commission (10%)</span>
                    <DollarSign className="stat-icon icon-gold" />
                  </div>
                  <h2 className="stat-value">${commissionCollected.toFixed(2)}</h2>
                  <p className="stat-subtitle">Total platform profit</p>
                </div>

                <div className="stat-card glass-panel">
                  <div className="stat-header">
                    <span className="stat-title">Active Registrations</span>
                    <User className="stat-icon icon-green" />
                  </div>
                  <h2 className="stat-value">{tutors.length} Tutors</h2>
                  <p className="stat-subtitle">Total Swahili educators</p>
                </div>
              </div>

              {/* Dashboard details */}
              <div className="grid-2 mt-5">
                <div className="panel glass-panel">
                  <h3>All Platform Bookings</h3>
                  <div className="mt-3">
                    {allBookings.map(b => (
                      <div key={b.id} className="booking-req-item glass-card p-3 mb-2 flex justify-between align-center text-sm">
                        <div>
                          <strong>{b.studentName} & Mwalimu {b.tutorName}</strong>
                          <p className="text-xs text-secondary">{new Date(b.scheduledDateTime).toLocaleString()}</p>
                        </div>
                        <span className={`badge badge-${b.status.toLowerCase()}`}>
                          {b.status}
                        </span>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="panel glass-panel">
                  <h3>Tutor Registrations Needing Verification</h3>
                  <p className="mb-4">You have {pendingTutorsList.length} tutors awaiting credential verification.</p>
                  {pendingTutorsList.length > 0 && (
                    <div className="tutor-mini-list">
                      {pendingTutorsList.map(t => (
                        <div key={t.id} className="tutor-mini-item glass-card p-3 mb-2 flex justify-between align-center">
                          <div>
                            <strong>Juma Bakari</strong>
                            <p className="text-xs text-secondary">MA in Linguistics</p>
                          </div>
                          <button className="btn-primary btn-sm" onClick={() => setCurrentTab('verification')}>
                            Verify
                          </button>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            </div>
          )}

          {currentTab === 'verification' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>Tutor Verification Portal</h1>
                <p>Inspect certificate uploads, check teaching experience, and approve or reject new tutors.</p>
              </div>

              {pendingTutorsList.length === 0 ? (
                <div className="glass-panel p-5 text-center">
                  <CheckCircle className="text-green mb-2" size={48} />
                  <p className="text-secondary">All tutor registration applications have been processed. Great job!</p>
                </div>
              ) : (
                <div className="tutors-verification-list">
                  {pendingTutorsList.map(t => (
                    <div key={t.id} className="tutor-verify-card glass-panel p-4 mb-4">
                      <div className="flex justify-between align-start">
                        <div>
                          <h2>Mwalimu {t.firstName} {t.lastName}</h2>
                          <p className="text-sm text-secondary mb-2">{t.email} • {t.phone}</p>
                        </div>
                        <span className="badge badge-pending">PENDING SUPERVISION</span>
                      </div>

                      <div className="mt-3 grid-2-1">
                        <div className="details-box text-sm text-secondary">
                          <p className="mb-2"><strong>Highest Qualification:</strong> {t.highestQualification}</p>
                          <p className="mb-2"><strong>Teaching Experience:</strong> {t.teachingExperience}</p>
                          <p className="mb-3"><strong>Bio Note:</strong> {t.bio || "No bio added."}</p>
                          
                          <div className="uploaded-files-section mt-3">
                            <h4 className="font-semibold text-gold mb-2">Submitted Certificates:</h4>
                            <div className="flex gap-2">
                              <span className="file-chip glass-card p-2 text-xs flex align-center gap-2">
                                <FileText size={14} /> Qualification_Degree.pdf (3.2 MB)
                              </span>
                            </div>
                          </div>
                        </div>

                        <div className="actions-box flex flex-column justify-center gap-2">
                          <button className="btn-primary w-full justify-center" onClick={() => handleApproveTutor(t.id)}>
                            <Check size={18} /> Approve & List
                          </button>
                          <button className="btn-secondary w-full justify-center" onClick={() => handleRejectTutor(t.id)}>
                            <XCircle size={18} className="text-danger" /> Reject Application
                          </button>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {currentTab === 'all-tutors' && (
            <div className="tab-pane">
              <div className="dashboard-header mb-4">
                <h1>Platform Tutors Inventory</h1>
                <p>Complete list of educators and their operational statuses.</p>
              </div>

              <div className="glass-panel p-4">
                <table className="admin-table w-full text-left text-sm text-secondary">
                  <thead>
                    <tr className="border-b py-2 text-gold font-semibold">
                      <th>Name</th>
                      <th>Email</th>
                      <th>Qualifications</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {tutors.map(t => (
                      <tr key={t.id} className="border-b py-3">
                        <td className="font-medium text-white">Mwalimu {t.firstName} {t.lastName}</td>
                        <td>{t.email}</td>
                        <td>{t.highestQualification}</td>
                        <td>
                          <span className={`badge badge-${t.status.toLowerCase()}`}>
                            {t.status}
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </main>
      </div>
    );
  };

  // --- RENDERING ROUTER SWAPPER ---
  return (
    <div className="app-container">
      {/* Alert Notification Popup banner */}
      {notification && (
        <div className={`notification-popup notification-${notification.type}`}>
          <AlertCircle size={18} />
          <span>{notification.message}</span>
        </div>
      )}

      {/* 1. If not logged in, show Welcome Landing / Auth Portal */}
      {!currentUser ? (
        <div className="auth-portal animate-fade-in">
          {/* Landing / Welcome Screen */}
          {authMode === 'landing' ? (
            <div className="landing-layout">
              <div className="landing-hero text-center p-5">
                <img src="/logo.png" alt="Soma Swahili Logo" className="landing-logo mb-4" />
                <div className="hero-badge mb-3">🎓 Soma Swahili Platform</div>
                <h1 className="text-5xl mb-4 font-bold text-gold">Jifunze Kiswahili Leo!</h1>
                <p className="text-lg text-secondary max-w-2xl mx-auto mb-5">
                  Learn standard Kiswahili (Kiswahili Sanifu) and rich East African cultural dialects from native verified tutors. Book live 1-on-1 sessions directly from your wallet balance.
                </p>
                <div className="flex justify-center gap-3">
                  <button className="btn-primary" onClick={() => setAuthMode('login')}>
                    Ingia (Login) <ArrowRight size={18} />
                  </button>
                  <button className="btn-secondary" onClick={() => { setAuthMode('register'); setAuthRole('STUDENT'); }}>
                    Jisajili kama Mwanafunzi (Join as Student)
                  </button>
                </div>
              </div>

              {/* Showcase highlights */}
              <div className="landing-highlights grid-3 max-w-5xl mx-auto mt-5 px-3">
                <div className="highlight-card glass-panel p-4 text-center">
                  <Wallet className="icon-gold mb-2" size={32} />
                  <h3>Secured Wallet Escrow</h3>
                  <p className="text-sm text-secondary mt-2">
                    Students top up their wallets. Funds are safely held until the tutor successfully delivers the lesson.
                  </p>
                </div>

                <div className="highlight-card glass-panel p-4 text-center">
                  <Shield className="icon-blue mb-2" size={32} />
                  <h3>Supervisor Verification</h3>
                  <p className="text-sm text-secondary mt-2">
                    Admins thoroughly verify tutor qualifications and certificates before allowing bookings.
                  </p>
                </div>

                <div className="highlight-card glass-panel p-4 text-center">
                  <BookOpen className="icon-green mb-2" size={32} />
                  <h3>Full Interactive Syllabus</h3>
                  <p className="text-sm text-secondary mt-2">
                    Browse courses and lessons offered by tutors to match your specific learning goals.
                  </p>
                </div>
              </div>
            </div>
          ) : (
            // Auth Form (Login / Register)
            <div className="auth-form-card glass-panel p-5">
              <div className="text-center mb-3">
                <img src="/logo.png" alt="Soma Swahili Logo" className="auth-logo" />
              </div>
              <div className="auth-header text-center mb-4">
                <h2>{authMode === 'login' ? 'Karibu Tena (Welcome Back)' : 'Jisajili (Create Account)'}</h2>
                <p className="text-secondary text-sm">
                  {authMode === 'login' ? 'Log in to your Swahili Portal' : 'Register to start learning or teaching'}
                </p>
              </div>

              {/* Role Switcher */}
              <div className="role-switcher-grid mb-4">
                <button className={`role-tab ${authRole === 'STUDENT' ? 'active' : ''}`} onClick={() => setAuthRole('STUDENT')}>
                  Mwanafunzi (Student)
                </button>
                <button className={`role-tab ${authRole === 'TUTOR' ? 'active' : ''}`} onClick={() => setAuthRole('TUTOR')}>
                  Mwalimu (Tutor)
                </button>
                <button className={`role-tab ${authRole === 'ADMIN' ? 'active' : ''}`} onClick={() => setAuthRole('ADMIN')}>
                  Supervisor (Admin)
                </button>
              </div>

              <form onSubmit={authMode === 'login' ? handleLogin : handleRegister}>
                <div className="form-group mb-3">
                  <label className="d-block text-sm mb-1">Barua Pepe (Email Address)</label>
                  <input 
                    type="email" 
                    className="form-input" 
                    placeholder="email@example.com" 
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                  {authRole === 'ADMIN' && authMode === 'login' && (
                    <span className="text-xs text-gold">Demo Admin: admin@somaswahili.com</span>
                  )}
                </div>

                <div className="form-group mb-4">
                  <label className="d-block text-sm mb-1">Nenosiri (Password)</label>
                  <input 
                    type="password" 
                    className="form-input" 
                    placeholder="••••••••" 
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                  {authRole === 'ADMIN' && authMode === 'login' && (
                    <span className="text-xs text-gold">Demo Password: admin123</span>
                  )}
                </div>

                {authMode === 'register' && (
                  <>
                    <div className="form-group mb-3">
                      <label className="d-block text-sm mb-1">First Name</label>
                      <input 
                        type="text" 
                        className="form-input" 
                        placeholder="John" 
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                      />
                    </div>
                    <div className="form-group mb-3">
                      <label className="d-block text-sm mb-1">Last Name</label>
                      <input 
                        type="text" 
                        className="form-input" 
                        placeholder="Doe" 
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                      />
                    </div>
                    <div className="form-group mb-3">
                      <label className="d-block text-sm mb-1">Phone Number</label>
                      <input 
                        type="text" 
                        className="form-input" 
                        placeholder="+254..." 
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                      />
                    </div>

                    {authRole === 'TUTOR' && (
                      <div className="tutor-special-fields glass-card p-3 mb-3">
                        <p className="text-sm font-semibold text-gold mb-2">Tutor Application Details</p>
                        
                        <div className="form-group mb-2">
                          <label className="text-xs">Highest Qualification</label>
                          <input 
                            type="text" 
                            className="form-input py-1" 
                            placeholder="BA in Languages, UDSM" 
                            value={highestQualification}
                            onChange={(e) => setHighestQualification(e.target.value)}
                          />
                        </div>

                        <div className="form-group mb-2">
                          <label className="text-xs">Teaching Experience</label>
                          <input 
                            type="text" 
                            className="form-input py-1" 
                            placeholder="3+ years tutoring" 
                            value={teachingExperience}
                            onChange={(e) => setTeachingExperience(e.target.value)}
                          />
                        </div>

                        <div className="form-group mb-2">
                          <label className="text-xs">Bio Summary</label>
                          <textarea 
                            className="form-input py-1" 
                            placeholder="Tell students about your Kiswahili lessons..." 
                            rows="2"
                            value={bio}
                            onChange={(e) => setBio(e.target.value)}
                          />
                        </div>

                        <div className="form-group">
                          <label className="text-xs">Intro Video URL</label>
                          <input 
                            type="text" 
                            className="form-input py-1" 
                            placeholder="https://youtube.com/..." 
                            value={videoUrl}
                            onChange={(e) => setVideoUrl(e.target.value)}
                          />
                        </div>
                      </div>
                    )}
                  </>
                )}

                <button type="submit" className="btn-primary w-full justify-center">
                  {authMode === 'login' ? 'Ingia (Login)' : 'Kamilisha Usajili (Submit)'}
                </button>
              </form>

              <div className="auth-footer text-center mt-4 text-sm text-secondary">
                {authMode === 'login' ? (
                  <p>
                    Don't have an account?{' '}
                    <button className="text-gold underline font-semibold bg-transparent border-0 cursor-pointer" onClick={() => setAuthMode('register')}>
                      Sign Up
                    </button>
                  </p>
                ) : (
                  <p>
                    Already have an account?{' '}
                    <button className="text-gold underline font-semibold bg-transparent border-0 cursor-pointer" onClick={() => setAuthMode('login')}>
                      Log In
                    </button>
                  </p>
                )}
                <button className="d-block mx-auto mt-2 text-xs text-blue bg-transparent border-0 cursor-pointer" onClick={() => setAuthMode('landing')}>
                  Back to Landing Page
                </button>
              </div>
            </div>
          )}
        </div>
      ) : (
        // 2. If logged in, show the appropriate Dashboard
        <>
          {currentUser.role === 'ADMIN' && renderAdminDashboard()}
          {currentUser.role === 'TUTOR' && renderTutorDashboard()}
          {currentUser.role === 'STUDENT' && renderStudentDashboard()}
        </>
      )}

      {/* --- POPUP MODAL: BOOKING SCHEDULER --- */}
      {selectedTutor && (
        <div className="modal-overlay flex justify-center align-center">
          <div className="modal-content glass-panel p-4 max-w-md w-full animate-fade-in">
            <div className="modal-header flex justify-between align-center mb-3">
              <h3>Schedule Swahili Class</h3>
              <button className="bg-transparent border-0 text-white cursor-pointer font-bold" onClick={() => setSelectedTutor(null)}>X</button>
            </div>
            <p className="text-sm text-secondary mb-3">
              You are booking a 1-on-1 session with <strong>Mwalimu {selectedTutor.firstName}</strong>.
            </p>

            <form onSubmit={handleCreateBooking}>
              <div className="form-group mb-3">
                <label className="d-block text-xs mb-1">Select Date</label>
                <input 
                  type="date" 
                  className="form-input" 
                  required
                  value={bookingDate}
                  onChange={(e) => setBookingDate(e.target.value)}
                />
              </div>

              <div className="form-group mb-3">
                <label className="d-block text-xs mb-1">Select Time Slot</label>
                <input 
                  type="time" 
                  className="form-input" 
                  required
                  value={bookingTime}
                  onChange={(e) => setBookingTime(e.target.value)}
                />
              </div>

              <div className="form-group mb-3">
                <label className="d-block text-xs mb-1">Duration</label>
                <select 
                  className="form-input" 
                  value={bookingDuration}
                  onChange={(e) => {
                    const dur = parseInt(e.target.value);
                    setBookingDuration(dur);
                    setBookingPrice(dur === 60 ? 25.00 : 12.50);
                  }}
                >
                  <option value="60">1 Hour ($25.00)</option>
                  <option value="30">30 Mins ($12.50)</option>
                </select>
              </div>

              {/* Wallet info warning */}
              <div className="wallet-warn-box glass-card p-3 mb-4 flex justify-between align-center">
                <div>
                  <p className="text-xs text-secondary">Wallet Balance:</p>
                  <strong className={studentWallet < bookingPrice ? "text-danger" : "text-green"}>
                    ${studentWallet.toFixed(2)}
                  </strong>
                </div>
                <div>
                  <p className="text-xs text-secondary">Required Fee:</p>
                  <strong>${bookingPrice.toFixed(2)}</strong>
                </div>
              </div>

              {studentWallet < bookingPrice && (
                <div className="alert alert-danger p-2 mb-3 rounded flex align-center gap-2 text-xs">
                  <AlertCircle size={14} className="text-danger" />
                  <span className="text-danger">Insufficient balance! Top up your wallet to book.</span>
                </div>
              )}

              <div className="flex gap-2">
                <button type="submit" className="btn-primary w-full justify-center" disabled={studentWallet < bookingPrice}>
                  Confirm & Secure Booking
                </button>
                <button type="button" className="btn-secondary" onClick={() => setSelectedTutor(null)}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* --- POPUP MODAL: WALLET DEPOSIT --- */}
      {showDepositModal && (
        <div className="modal-overlay flex justify-center align-center">
          <div className="modal-content glass-panel p-4 max-w-sm w-full animate-fade-in">
            <div className="modal-header flex justify-between align-center mb-3">
              <h3>Refill Wallet</h3>
              <button className="bg-transparent border-0 text-white cursor-pointer font-bold" onClick={() => setShowDepositModal(false)}>X</button>
            </div>
            <p className="text-sm text-secondary mb-4">
              Enter the amount you wish to add. Mobile money (M-Pesa) prompts will be simulated.
            </p>

            <form onSubmit={handleDeposit}>
              <div className="form-group mb-4">
                <label className="d-block text-xs mb-1">Deposit Amount (USD)</label>
                <input 
                  type="number" 
                  className="form-input" 
                  placeholder="50.00" 
                  min="5" 
                  required
                  value={depositAmount}
                  onChange={(e) => setDepositAmount(e.target.value)}
                />
              </div>

              <div className="flex gap-2">
                <button type="submit" className="btn-primary w-full justify-center">
                  Trigger Mock Deposit
                </button>
                <button type="button" className="btn-secondary" onClick={() => setShowDepositModal(false)}>
                  Close
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
