!
function(e, t) {
	"object" == typeof exports && "object" == typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define("AegisReport", [], t) : "object" == typeof exports ? exports.AegisReport = t() : e.AegisReport = t()
} (window, (function() {
	return function(e) {
		var t = {};
		function n(o) {
			if (t[o]) return t[o].exports;
			var i = t[o] = {
				i: o,
				l: !1,
				exports: {}
			};
			return e[o].call(i.exports, i, i.exports, n),
			i.l = !0,
			i.exports
		}
		return n.m = e,
		n.c = t,
		n.d = function(e, t, o) {
			n.o(e, t) || Object.defineProperty(e, t, {
				enumerable: !0,
				get: o
			})
		},
		n.r = function(e) {
			"undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {
				value: "Module"
			}),
			Object.defineProperty(e, "__esModule", {
				value: !0
			})
		},
		n.t = function(e, t) {
			if (1 & t && (e = n(e)), 8 & t) return e;
			if (4 & t && "object" == typeof e && e && e.__esModule) return e;
			var o = Object.create(null);
			if (n.r(o), Object.defineProperty(o, "default", {
				enumerable: !0,
				value: e
			}), 2 & t && "string" != typeof e) for (var i in e) n.d(o, i,
			function(t) {
				return e[t]
			}.bind(null, i));
			return o
		},
		n.n = function(e) {
			var t = e && e.__esModule ?
			function() {
				return e.
			default
			}:
			function() {
				return e
			};
			return n.d(t, "a", t),
			t
		},
		n.o = function(e, t) {
			return Object.prototype.hasOwnProperty.call(e, t)
		},
		n.p = "",
		n(n.s = 1)
	} ([function(e, t, n) {
		e.exports = function() {
			var e, t;
			function n(t) {
				this.name = "__st" + (1e9 * Math.random() >>> 0) + e + "__",
				null == t || t.forEach(this.add, this),
				e += 1
			}
			Array.prototype.find || Object.defineProperty(Array.prototype, "find", {
				configurable: !0,
				writable: !0,
				value: function(e) {
					if (null === this) throw new TypeError('"this" is null or not defined');
					var t = Object(this),
					n = t.length >>> 0;
					if ("function" != typeof e) throw new TypeError("predicate must be a function");
					for (var o = arguments[1], i = 0; i < n;) {
						var r = t[i];
						if (e.call(o, r, i, t)) return r;
						i += 1
					}
				}
			}),
			String.prototype.startsWith || Object.defineProperty(String.prototype, "startsWith", {
				value: function(e, t) {
					return this.substring(t = !t || t < 0 ? 0 : +t, t + e.length) === e
				}
			}),
			window.WeakSet || (e = Date.now() % 1e9, n.prototype.add = function(e) {
				var t = this.name;
				return e[t] || Object.defineProperty(e, t, {
					value: !0,
					writable: !0
				}),
				this
			},
			n.prototype.delete = function(e) {
				return !! e[this.name] && !(e[this.name] = void 0)
			},
			n.prototype.has = function(e) {
				return !! e[this.name]
			},
			t = n, Object.defineProperty(window, "WeakSet", {
				value: function(e) {
					return new t(e)
				}
			})),
			Object.assign || Object.defineProperty(Object, "assign", {
				enumerable: !1,
				configurable: !0,
				writable: !0,
				value: function(e) {
					if (null == e) throw new TypeError("Cannot convert first argument to object");
					for (var t = Object(e), n = 1; n < arguments.length; n++) if (null != (o = arguments[n])) for (var o = Object(o), i = Object.keys(Object(o)), r = 0, s = i.length; r < s; r++) {
						var a = i[r],
						u = Object.getOwnPropertyDescriptor(o, a);
						null != u && u.enumerable && (t[a] = o[a])
					}
					return t
				}
			});
			var o = function(e, t) {
				return (o = Object.setPrototypeOf || {
					__proto__: []
				}
				instanceof Array &&
				function(e, t) {
					e.__proto__ = t
				} ||
				function(e, t) {
					for (var n in t) Object.prototype.hasOwnProperty.call(t, n) && (e[n] = t[n])
				})(e, t)
			},
			i = function() {
				return (i = Object.assign ||
				function(e) {
					for (var t, n = 1,
					o = arguments.length; n < o; n++) for (var i in t = arguments[n]) Object.prototype.hasOwnProperty.call(t, i) && (e[i] = t[i]);
					return e
				}).apply(this, arguments)
			};
			function r(e) {
				try {
					return encodeURIComponent(decodeURIComponent(e))
				} catch(t) {
					return e
				}
			}
			var s, a = (c.prototype.indexOf = function(e, t) {
				for (var n = 0; n < e.length; n++) if (e[n].callback === t) return n;
				return - 1
			},
			c.prototype.on = function(e, t, n) {
				if (void 0 === n && (n = 0), this) {
					var o = this.eventsList[e];
					return o || (this.eventsList[e] = [], o = this.eventsList[e]),
					-1 !== this.indexOf(o, t) || o.push({
						name: e,
						type: n || 0,
						callback: t
					}),
					this
				}
			},
			c.prototype.one = function(e, t) {
				this.on(e, t, 1)
			},
			c.prototype.remove = function(e, t) {
				if (this) {
					var n = this.eventsList[e];
					if (!n) return null;
					if (t) return n.length && (t = this.indexOf(n, t), n.splice(t, 1)),
					this;
					try {
						delete this.eventsList[e]
					} catch(e) {}
					return null
				}
			},
			c.prototype.clear = function() {
				this.eventsList = {}
			},
			c),
			u = function(e) {
				return (e = Array.isArray(e) ? e: [e]).map((function(e, t) {
					return Object.getOwnPropertyNames(e).map((function(n) {
						return r(n) + "[" + t + "]=" + (void 0 === e[n] ? "": r(e[n]))
					})).join("&")
				})).join("&") + (e.length ? "&count=" + e.length: "")
			};
			function c() {
				var e = this;
				this.emit = function(t, n) {
					if (e) {
						var o;
						if (null != (i = e.eventsList[t]) && i.length) for (var i = i.slice(), r = 0; r < i.length; r++) {
							o = i[r];
							try {
								var s = o.callback.apply(e, [n]);
								if (1 === o.type && e.remove(t, o.callback), !1 === s) break
							} catch(t) {
								throw t
							}
						}
						return e
					}
				},
				this.eventsList = {}
			}
			function l(e, t) {
				return "number" == typeof e || "string" == typeof e ? e: t ? s.string: s.number
			}
			function f(e, t) {
				return "string" == typeof e ? e.split("?")[t ? 1 : 0] || "": e
			}
			function d(e) {
				return "string" == typeof e && e.startsWith("//") ? "undefined" != typeof location && "https:" === location.protocol: /^https/.test(e)
			}
			function p(e) {
				return y.some((function(t) {
					return - 1 !== e.indexOf(t)
				}))
			}
			function h(e, t) {
				var n;
				try {
					"string" == typeof e && (e = JSON.parse(e)),
					"function" == typeof(null === (n = null == t ? void 0 : t.ret) || void 0 === n ? void 0 : n.join) && (w = [].concat(t.ret.map((function(e) {
						return e.toLowerCase()
					}))));
					var o = Object.getOwnPropertyNames(e).filter((function(e) {
						return - 1 !== w.indexOf(e.toLowerCase())
					}));
					return o.length ? "" + e[o[0]] : "unknown"
				} catch(e) {
					return "unknown"
				}
			}
			function g(e, t) {
				void 0 === t && (t = 3);
				var n, o, i, r = "";
				return Array.isArray(e) ? (r += "[", n = e.length, e.forEach((function(e, o) {
					var i, s;
					r += "object" == typeof e && 1 < t ? g(e, t - 1) : (s = "", s += "undefined" == (e = typeof(i = e)) || "symbol" == e || "function" == e ? "null": "string" == e || "object" == e ? '"' + i + '"': i),
					r += o === n - 1 ? "": ","
				})), r += "]") : e instanceof Object ? (r = "{", o = Object.keys(e), i = o.length, o.forEach((function(n, s) {
					var a, u, c;
					"object" == typeof e[n] && 1 < t ? r += '"' + n + '":' + g(e[n], t - 1) : r += (c = "", "string" == (n = typeof(u = e[a = n])) || "object" == n ? c += '"' + a + '":"' + u + '"': "function" == typeof u ? c += '"' + a + '":"function ' + u.name + '"': "symbol" == typeof u ? c += '"' + a + '":"symbol"': "number" != typeof u && "boolean" != n || (c += '"' + a + '": ' + u), c),
					r += s === i - 1 || s < i - 1 && void 0 === e[o[s + 1]] ? "": ","
				})), r += "}") : r += e,
				r
			} (X = s = s || {})[X.number = -1] = "number",
			X.string = "";
			var m, v, y = ["application/octet-stream", "application/xhtml+xml", "application/xml", "application/pdf", "application/pkcs12", "application/javascript", "application/ecmascript", "application/vnd.mspowerpoint", "application/ogg", "text/html", "text/css", "text/javascript", "image", "audio", "video"],
			w = ["ret", "retcode", "code"],
			b = function(e) {
				if ("string" == typeof e) return e;
				try {
					return (JSON.stringify(e, (t = [], n = [],
					function(e, o) {
						if (o instanceof Error) return "Error.message: " + o.message + " \n  Error.stack: " + o.stack;
						if ("object" == typeof o && null !== o) {
							var i = t.indexOf(o);
							if ( - 1 !== i) return "[Circular " + n[i] + "]";
							t.push(o),
							n.push(e || "root")
						}
						return o
					}), 4) || "undefined").replace(/"/gim, "")
				} catch(e) {
					return "error happen when aegis stringify: \n " + e.message + " \n " + e.stack
				}
				var t, n
			}, E = (S.prototype.sourceURL = function() {
				return this.data.responseURL
			},
			S.prototype.status = function() {
				return Number(this.data.status)
			},
			S.prototype.headers = function() {
				var e = this.data.getAllResponseHeaders().split("\n"),
				t = {};
				return e.forEach((function(e) {
					var n;
					e && (e = (n = e.split(": "))[0], n = n[1].trim(), t[e] = n)
				})),
				t
			},
			S),
			O = (R.prototype.sourceURL = function() {
				return this.data.url
			},
			R.prototype.status = function() {
				return Number(this.data.status)
			},
			R.prototype.headers = function() {
				var e = {};
				return this.data.headers.forEach((function(t, n) {
					e[n] = t
				})),
				e
			},
			R);
			function R(e, t) {
				this.type = "fetch",
				this.data = e || {},
				this.data.response = t
			}
			function S(e) {
				this.type = "xhr",
				this.data = e
			}
			function T(e, t) {
				var n, o = [],
				i = e.config;
				return e.lifeCycle.on("destroy", (function() {
					o.length = 0
				})),
				function(e, r) {
					if (o.push(e), t && o.length >= t) return o = _(o),
					r(o.splice(0, o.length)),
					void(n && clearTimeout(n));
					n && clearTimeout(n),
					n = setTimeout((function() {
						n = null,
						0 < (o = _(o)).length && r(o.splice(0, o.length))
					}), i.delay)
				}
			}
			function L(e, t) {
				return Array.isArray(e) ? t(e.map((function(e) {
					return i(i({},
					e), {
						msg: "string" == typeof e.msg ? e.msg: [].concat(e.msg).map(b).join(" ")
					})
				}))) : t(i(i({},
				e), {
					msg: "string" == typeof e.msg ? e.msg: b(e.msg)
				}))
			}
			function x(e) {
				if (!e || !e.reduce || !e.length) throw new TypeError("createPipeline need at least one function param");
				return 1 === e.length ?
				function(t, n) {
					e[0](t, n || q)
				}: e.reduce((function(e, t) {
					return function(n, o) {
						return void 0 === o && (o = q),
						e(n, (function(e) {
							return null == t ? void 0 : t(e, o)
						}))
					}
				}))
			}
			function P(e, t) {
				Object.getOwnPropertyNames(e).forEach((function(n) {
					"function" == typeof e[n] && "constructor" !== n && (t ? t[n] = function() {}: e[n] = function() {})
				}))
			}
			function C() {
				return void 0 !== window.performance && "function" == typeof performance.clearResourceTimings && "function" == typeof performance.getEntriesByType && "function" == typeof performance.now
			}
			function I(e) {
				if (W.find((function(t) {
					return t.name === e.name
				}))) throw new Error("name '" + e.name + "' is already in hackXHR option list");
				var t, n;
				W.push(e),
				!V && window.XMLHttpRequest && (V = !0, t = window.XMLHttpRequest.prototype.send, n = window.XMLHttpRequest.prototype.open, window.XMLHttpRequest.prototype.open = function() {
					if (this.aegisMethod = arguments[0], this.aegisUrl = arguments[1], this.aegisXhrStartTime = Date.now(), !this.sendByAegis) for (var e = 0; e < W.length; e++) {
						var t = W[e];
						try {
							"function" == typeof t.open && t.open(this)
						} catch(e) {}
					}
					return n.apply(this, arguments)
				},
				window.XMLHttpRequest.prototype.send = function() {
					if (!this.sendByAegis) for (var e = 0; e < W.length; e++) {
						var n = W[e];
						try {
							"function" == typeof n.send && n.send(this, arguments[0])
						} catch(e) {}
					}
					return t.apply(this, arguments)
				})
			}
			function A(e) {
				var t = W.findIndex((function(t) {
					return t.name === e.name
				})); - 1 !== t && W.splice(t, 1)
			} ($ = m = m || {}).INFO_ALL = "-1",
			$.API_RESPONSE = "1",
			$.INFO = "2",
			$.ERROR = "4",
			$.PROMISE_ERROR = "8",
			$.AJAX_ERROR = "16",
			$.SCRIPT_ERROR = "32",
			$.IMAGE_ERROR = "64",
			$.CSS_ERROR = "128",
			$.CONSOLE_ERROR = "256",
			$.MEDIA_ERROR = "512",
			$.RET_ERROR = "1024",
			$.REPORT = "2048",
			$.PV = "4096",
			$.EVENT = "8192",
			(Ae = xe = xe || {})[Ae.android = 1] = "android",
			Ae[Ae.ios = 2] = "ios",
			Ae[Ae.windows = 3] = "windows",
			Ae[Ae.macos = 4] = "macos",
			Ae[Ae.linux = 5] = "linux",
			Ae[Ae.devtools = 6] = "devtools",
			Ae[Ae.other = 100] = "other",
			(ce = se = se || {})[ce.unknown = 100] = "unknown",
			ce[ce.wifi = 1] = "wifi",
			ce[ce.net2g = 2] = "net2g",
			ce[ce.net3g = 3] = "net3g",
			ce[ce.net4g = 4] = "net4g",
			ce[ce.net5g = 5] = "net5g",
			ce[ce.net6g = 6] = "net6g",
			(fe = v = v || {}).LOG = "log",
			fe.SPEED = "speed",
			fe.PERFORMANCE = "performance",
			fe.OFFLINE = "offline",
			fe.WHITE_LIST = "whiteList",
			fe.VITALS = "vitals",
			fe.PV = "pv",
			fe.CUSTOM_PV = "customPV",
			fe.EVENT = "event",
			fe.CUSTOM = "custom",
			fe.SDK_ERROR = "sdkError";
			var N, k, _ = function(e) {
				return e.filter((function(t, n) {
					return "static" !== t.type || !e.find((function(e, o) {
						return n !== o && t.url === e.url && 200 === t.status
					}))
				}))
			},
			j = function(e) {
				e.level === m.INFO_ALL && (e.level = m.INFO)
			},
			U = function(e) {
				return function(t, n) {
					return e.send({
						url: e.config.url || "",
						data: u(t),
						method: "post",
						contentType: "application/x-www-form-urlencoded",
						type: v.LOG,
						log: t
					},
					(function() {
						var o = e.config.onReport;
						"function" == typeof o && t.forEach((function(e) {
							o(e)
						})),
						"function" == typeof n && n([])
					}), (function(t) {
						"403 forbidden" === t && e.destroy()
					}))
				}
			},
			q = function() {},
			F = (K.prototype.init = function(e) {
				this.setConfig(e);
				for (var t = 0; t < K.installedPlugins.length; t++) try {
					K.installedPlugins[t].patch(this)
				} catch(e) {
					this.sendSDKError(e)
				}
				this.lifeCycle.emit("onInited")
			},
			K.prototype.setConfig = function(e) {
				Object.assign(this.config, e);
				var t = (a = this.config).id,
				n = a.uin,
				o = a.version,
				i = a.ext1,
				r = a.ext2,
				s = a.ext3,
				a = (e = a.aid, this.bean.id !== t || this.bean.uin !== n || this.bean.aid !== e);
				return this.bean.id = t || "",
				this.bean.uin = n || "",
				this.bean.version = o || "1.34.64",
				this.bean.aid = e || "",
				i && (this.bean.ext1 = i),
				r && (this.bean.ext2 = r),
				s && (this.bean.ext3 = s),
				a && this.lifeCycle.emit("onConfigChange", this.config),
				this.config
			},
			K.use = function(e) { - 1 === K.installedPlugins.indexOf(e) && e.aegisPlugin && K.installedPlugins.push(e)
			},
			K.unuse = function(e) { - 1 !== (e = K.installedPlugins.indexOf(e)) && K.installedPlugins.splice(e, 1)
			},
			K.prototype.info = function() {
				for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
				var n = {
					level: m.INFO,
					msg: e
				};
				1 === e.length && e[0].msg && Object.assign(n, i({},
				e[0]), {
					level: m.INFO
				}),
				this.normalLogPipeline(n)
			},
			K.prototype.infoAll = function() {
				for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
				var n = {
					level: m.INFO_ALL,
					msg: e
				};
				1 === e.length && e[0].msg && Object.assign(n, i({},
				e[0]), {
					level: m.INFO_ALL
				}),
				this.normalLogPipeline(n)
			},
			K.prototype.report = function() {
				for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
				var n = {
					level: m.REPORT,
					msg: e
				};
				1 === e.length && e[0].msg && Object.assign(n, i({},
				e[0])),
				this.normalLogPipeline(n)
			},
			K.prototype.error = function() {
				for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
				var n = {
					level: m.ERROR,
					msg: e
				};
				1 === e.length && e[0].msg && Object.assign(n, i({},
				e[0]), {
					level: m.ERROR
				}),
				this.normalLogPipeline(n)
			},
			K.prototype.speedLogPipeline = function(e) {
				throw new Error('You need to override "speedLogPipeline" method')
			},
			K.prototype.reportPv = function(e) {
				var t, n = this;
				e && (console.warn("reportPv is deprecated, please use reportEvent"), t = "" + Object.getOwnPropertyNames(this.bean).filter((function(e) {
					return "id" !== e
				})).map((function(e) {
					return e + "=" + n.bean[e]
				})).join("&"), this.send({
					url: this.config.url + "/" + e + "?" + t,
					addBean: !1,
					type: v.CUSTOM_PV
				},
				(function() {}), (function(e) {
					"403 forbidden" === e && n.destroy()
				})))
			},
			K.prototype.reportEvent = function(e) {
				e && ((e = "string" == typeof e ? {
					name: e,
					ext1: this.config.ext1 || "",
					ext2: this.config.ext2 || "",
					ext3: this.config.ext3 || ""
				}: e).name ? this.eventPipeline(e) : console.warn("reportEvent params error"))
			},
			K.prototype.reportTime = function(e, t) {
				if ("object" == typeof e) return this.reportT(e);
				"string" == typeof e ? "number" == typeof t ? t < 0 || 6e4 < t ? console.warn("reportTime: duration must between 0 and 60000") : this.submitCustomTime(e, t) : console.warn("reportTime: second param must be number") : console.warn("reportTime: first param must be a string")
			},
			K.prototype.reportT = function(e) {
				var t = e.name,
				n = e.duration,
				o = void 0 === (i = e.ext1) ? "": i,
				i = void 0 === (r = e.ext2) ? "": r,
				r = void 0 === (r = e.ext3) ? "": r;
				if (e = e.from, "string" == typeof t && "number" == typeof n && "string" == typeof o && "string" == typeof i && "string" == typeof r) {
					if (! (n < 0 || 6e4 < n)) return this.submitCustomTime(t, n, o, i, r, void 0 === e ? "": e);
					console.warn("reportTime: duration must between 0 and 60000")
				} else console.warn("reportTime: params error")
			},
			K.prototype.time = function(e) {
				"string" == typeof e ? this.timeMap[e] ? console.warn("Timer " + e + " already exists") : this.timeMap[e] = Date.now() : console.warn("time: first param must be a string")
			},
			K.prototype.timeEnd = function(e) {
				"string" == typeof e ? this.timeMap[e] ? (this.submitCustomTime(e, Date.now() - this.timeMap[e]), delete this.timeMap[e]) : console.warn("Timer " + e + " does not exist") : console.warn("timeEnd: first param must be a string")
			},
			K.prototype.submitCustomTime = function(e, t, n, o, i, r) {
				this.customTimePipeline({
					name: e,
					duration: t,
					ext1: n || this.config.ext1,
					ext2: o || this.config.ext2,
					ext3: i || this.config.ext3,
					from: r || void 0
				})
			},
			K.prototype.extendBean = function(e, t) {
				this.bean[e] = t
			},
			K.prototype.send = function(e, t, n) {
				var o, i, r, s = this;
				x([(i = e.type,
				function(e, t) {
					if (!e) return t(e);
					e = Array.isArray(e) ? e: [e],
					o.lifeCycle.emit("beforeRequest", e);
					var n = o.config.beforeRequest; (e = "function" == typeof n ? e.filter((function(e) {
						return ! 1 !== n({
							logs: e,
							logType: i
						})
					})) : e).length && t(e)
				}),
				function(o, i) {
					s.request(e, (function() {
						for (var n = [], r = 0; r < arguments.length; r++) n[r] = arguments[r];
						i({
							isErr: !1,
							result: n,
							logType: e.type,
							logs: o
						}),
						null == t || t.apply(void 0, n)
					}), (function() {
						for (var t = [], r = 0; r < arguments.length; r++) t[r] = arguments[r];
						i({
							isErr: !0,
							result: t,
							logType: e.type,
							logs: o
						}),
						null == n || n.apply(void 0, t)
					}))
				},
				(r = o = this,
				function(e, t) {
					r.lifeCycle.emit("afterRequest", e);
					var n = r.config.afterRequest;
					"function" == typeof n && !1 === n(e) || t(e)
				})])(e.log)
			},
			K.prototype.request = function(e, t, n) {
				throw new Error('You need to override "request" method')
			},
			K.prototype.sendSDKError = function(e) {
				this.send({
					url: this.config.url + "?id=1085&msg[0]=" + encodeURIComponent(b(e)) + "&level[0]=2&from=" + this.config.id + "&count=1&version=" + this.config.id + "(1.34.64)",
					addBean: !1,
					method: "get",
					type: v.SDK_ERROR
				})
			},
			K.prototype.destroy = function(e) {
				void 0 === e && (e = !1);
				var t, n, o = K.instances.indexOf(this); - 1 !== o && K.instances.splice(o, 1);
				for (var i = K.installedPlugins.length - 1; 0 <= i; i--) try {
					K.installedPlugins[i].unpatch(this)
				} catch(e) {
					this.sendSDKError(e)
				}
				if (this.lifeCycle.emit("destroy"), this.lifeCycle.clear(), e) t = this,
				n = Object.getOwnPropertyDescriptors(t),
				Object.keys(n).forEach((function(e) {
					n[e].writable && (t[e] = null)
				})),
				Object.setPrototypeOf(this, null);
				else {
					for (var r = this; r.constructor !== Object && P(r, this), r = Object.getPrototypeOf(r););
					0 === K.instances.length && (P(e = Object.getPrototypeOf(this).constructor), P(K))
				}
			},
			K.instances = [], K.LOG_TYPE = m, K.installedPlugins = [], K),
			D = (Q.prototype.patch = function(e) {
				this.canUse(e) && this.exist(e) && (this.instances.push(e), this.triggerInit(e), this.triggerOnNewAegis(e))
			},
			Q.prototype.unpatch = function(e) { - 1 !== (e = this.instances.indexOf(e)) && this.instances.splice(e, 1)
			},
			Q.prototype.countInstance = function() {
				return this.instances.length
			},
			Q.prototype.uninstall = function() {
				var e;
				null === (e = null === (e = this.option) || void 0 === e ? void 0 : e.destroy) || void 0 === e || e.apply(this)
			},
			Q.prototype.walk = function(e) {
				var t = this;
				this.instances.forEach((function(n) {
					var o = t.canUse(n);
					o && e(n, o)
				}))
			},
			Q.prototype.canUse = function(e) {
				return ! (! (e = this.getConfig(e)) || "object" != typeof e) || !!e
			},
			Q.prototype.getConfig = function(e) {
				return null === (e = e.config) || void 0 === e ? void 0 : e[this.name]
			},
			Q.prototype.exist = function(e) {
				return - 1 === this.instances.indexOf(e)
			},
			Q.prototype.triggerInit = function(e) {
				var t;
				this.inited || (this.inited = !0, null === (t = null === (t = this.option) || void 0 === t ? void 0 : t.init) || void 0 === t || t.call(this.option, this.getConfig(e)))
			},
			Q.prototype.triggerOnNewAegis = function(e) {
				var t;
				null === (t = null === (t = this.option) || void 0 === t ? void 0 : t.onNewAegis) || void 0 === t || t.call(this.option, e, this.getConfig(e))
			},
			Q),
			M = new D({
				name: "aid",
				aid: "",
				init: function(e) {
					try {
						var t = !0 !== e && e || window.localStorage.getItem("AEGIS_ID");
						t || (t = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, (function(e) {
							var t = 16 * Math.random() | 0;
							return ("x" === e ? t: 3 & t | 8).toString(16)
						})), window.localStorage.setItem("AEGIS_ID", t)),
						this.aid = t
					} catch(e) {}
				},
				onNewAegis: function(e) {
					e.bean.aid = this.aid,
					e.config.aid = this.aid
				}
			}),
			H = function(e) {
				if (e.payload) {
					var t = {};
					return Object.keys(e).forEach((function(n) {
						"payload" !== n && (t[n] = e[n])
					})),
					t
				}
				return e
			},
			B = new D({
				name: "reportAssetSpeed"
			}),
			X = B = new D({
				name: "reportAssetSpeed",
				collectCur: 0,
				ASSETS_INITIATOR_TYPE: ["img", "css", "script", "link", "audio", "video"],
				onNewAegis: function(e) {
					var t = this;
					C() && (this.collectSuccLog(e), this.collectFailLog(e), performance.onresourcetimingbufferfull = function() {
						t.collectCur = 0,
						performance.clearResourceTimings()
					})
				},
				publish: function(e) {
					this.$walk((function(t) {
						t.speedLogPipeline(e)
					}))
				},
				collectSuccLog: function(e) {
					function t(e) {
						for (var t = 0,
						n = e.length; t < n; t++) {
							var o = e[t]; - 1 !== i.ASSETS_INITIATOR_TYPE.indexOf(o.initiatorType) && -1 === o.name.indexOf("cdn-go.cn/aegis/aegis-sdk") && i.publish(i.generateLog(o, r))
						}
					}
					var n, o, i = this,
					r = e.config;
					"function" == typeof window.PerformanceObserver ? (t(performance.getEntriesByType("resource")), (n = new window.PerformanceObserver((function(e) {
						t(e.getEntries())
					}))).observe({
						entryTypes: ["resource"]
					}), e.lifeCycle.on("destroy", (function() {
						0 === B.countInstance() && n.disconnect()
					}))) : (o = setInterval((function() {
						var e = performance.getEntriesByType("resource"),
						n = e.slice(i.collectCur);
						i.collectCur = e.length,
						t(n)
					}), 3e3), e.lifeCycle.on("destroy", (function() {
						0 === B.countInstance() && clearInterval(o)
					})))
				},
				collectFailLog: function(e) {
					function t(e) {
						var t, i;
						e && (e = e.target || e.srcElement, t = (null == e ? void 0 : e.src) || (null == e ? void 0 : e.href), e = null == e ? void 0 : e.tagName, t && "string" == typeof t && ("IMG" === e && -1 < window.location.href.indexOf(t) || (e = "function" == typeof(null === (e = o.api) || void 0 === e ? void 0 : e.resourceTypeHandler) ? null === (i = o.api) || void 0 === i ? void 0 : i.resourceTypeHandler(t) : "", i = performance.getEntriesByType("resource").find((function(e) {
							return e.name === t
						})), e = {
							url: f(t),
							status: 400,
							duration: Number(((null == i ? void 0 : i.duration) || 0).toFixed(2)),
							method: "get",
							type: e || "static",
							isHttps: d(t),
							urlQuery: f(t, !0),
							domainLookup: 0,
							connectTime: 0
						},
						n.publish(e))))
					}
					var n = this,
					o = e.config;
					window.document.addEventListener("error", t, !0),
					e.lifeCycle.on("destroy", (function() {
						0 === B.countInstance() && window.document.removeEventListener("error", t, !0)
					}))
				},
				generateLog: function(e, t) {
					var n, o = "function" == typeof(null === (n = t.api) || void 0 === n ? void 0 : n.resourceTypeHandler) ? null === (o = t.api) || void 0 === o ? void 0 : o.resourceTypeHandler(e.name) : "";
					return {
						url: f(e.name),
						method: "get",
						duration: Number(e.duration.toFixed(2)),
						status: 200,
						type: o || "static",
						isHttps: d(e.name),
						urlQuery: f(e.name, !0),
						domainLookup: l(e.domainLookupEnd - e.domainLookupStart),
						connectTime: l(e.connectEnd - e.connectStart)
					}
				},
				destroy: function() {
					this.option.publish = function() {}
				}
			}),
			V = !1,
			W = [],
			J = !1,
			G = [],
			$ = (new D({
				name: "reportApiSpeed"
			}), new D({
				name: "reportApiSpeed",
				override: !1,
				onNewAegis: function(e) {
					this.override || (this.override = !0, this.overrideFetch(e.config), this.overrideXhr(e.config))
				},
				overrideFetch: function(e) {
					var t = this,
					n = {
						name: this.name,
						then: function(n, o, i, r) {
							var a, u, c, l;
							e.hostUrl && -1 < i.indexOf(e.hostUrl) || (u = {
								url: n.url,
								isHttps: d(n.url),
								method: (null == r ? void 0 : r.method) || "get",
								duration: o,
								type: "fetch",
								status: n.status || 0
							},
							"fetch" !== (l = "function" == typeof(null === (a = e.api) || void 0 === a ? void 0 : a.resourceTypeHandler) ? null === (c = e.api) || void 0 === c ? void 0 : c.resourceTypeHandler(n.url) : l) && "static" !== l && (c = n.headers ? n.headers.get("content-type") : "", l = n.ok && "string" == typeof c && p(c) ? "static": "fetch"), "fetch" === l ? n.clone().text().then((function(s) {
								var a, c, l, f = null === (d = e.api) || void 0 === d ? void 0 : d.apiDetail,
								d = n.status <= 0 || 400 <= n.status;
								f = (d ? "FETCH_ERROR: " + s + " \n\n": "") + "req url: " + i + " \n                                \nreq method: " + ((null == r ? void 0 : r.method) || "get") + " \n                                \nreq param: " + (f ? g(null == r ? void 0 : r.body) : "") + " \n                                \nres duration: " + o + " \n                                \nres status: " + (n.status || 0) + " \n                                \nres data: " + (f ? s: ""),
								t.publishNormalLog({
									msg: f,
									level: d ? m.AJAX_ERROR: m.API_RESPONSE
								}),
								u.payload = new O(n, s),
								("function" == typeof(null === (d = e.api) || void 0 === d ? void 0 : d.retCodeHandler) ? (l = (c = e.api.retCodeHandler(s, n.url, n) || {}).code, a = c.isErr, u.ret = l, a) : (c = h(s, e.api), u.ret = c, s = (s = null === (a = e.api) || void 0 === a ? void 0 : a.errCode) && [].concat(s), l = (l = null === (a = e.api) || void 0 === a ? void 0 : a.code) && [].concat(l), s && -1 !== s.indexOf(c) || l && -1 === l.indexOf(c) || !s && !l && "0" !== c && "unknown" !== c)) && t.publishNormalLog({
									msg: f,
									level: m.RET_ERROR
								})
							})) : Object.assign(u, {
								type: "static",
								urlQuery: f(n.url, !0),
								domainLookup: s.number,
								connectTime: s.number
							}), t.publishSpeed(u))
						},
						catch: function(n, o, i, r) {
							if (! (e.hostUrl && -1 < i.indexOf(e.hostUrl))) {
								var s = {
									url: i,
									isHttps: d(i),
									method: (null == r ? void 0 : r.method) || "get",
									duration: o,
									type: "fetch",
									status: 0
								};
								throw t.publishSpeed(s),
								s = null === (s = e.api) || void 0 === s ? void 0 : s.apiDetail,
								r = "AJAX_ERROR: " + n + "\n                          \nres status: 0\n                          \nres duration: " + o + "\n                          \nreq url: " + i + "\n                          \nreq method: " + ((null == r ? void 0 : r.method) || "get") + "\n                          \nreq param: " + (s ? g(null == r ? void 0 : r.body) : ""),
								t.publishNormalLog({
									msg: r,
									level: m.AJAX_ERROR
								}),
								n
							}
						}
					};
					this.hackFetchOptions = n,
					function(e) {
						if (G.find((function(t) {
							return t.name === e.name
						}))) throw new Error("name '" + e.name + "' is already in hackFetch option list");
						var t;
						G.push(e),
						!J && window.fetch && (J = !0, t = window.fetch, window.fetch = function(e, n) {
							void 0 === n && (n = {});
							for (var o = "string" == typeof e ? e: e.url, i = 0; i < G.length; i++) {
								var r = G[i];
								try {
									"function" == typeof r.beforeFetch && r.beforeFetch(o, n)
								} catch(e) {}
							}
							var s = Date.now();
							return t(e, n).then((function(e) {
								for (var t = 0; t < G.length; t++) {
									var i = G[t];
									try {
										"function" == typeof i.then && i.then(e, Date.now() - s, o, n)
									} catch(e) {}
								}
								return e
							})).
							catch((function(e) {
								for (var t = 0; t < G.length; t++) {
									var i = G[t];
									try {
										"function" == typeof i.
										catch && i.
										catch(e, Date.now() - s, o, n)
									} catch(e) {}
								}
								throw e
							}))
						})
					} (this.hackFetchOptions)
				},
				getRequestType: function(e, t, n) {
					var o, i = "function" == typeof(null === (o = e.api) || void 0 === o ? void 0 : o.resourceTypeHandler) ? null === (i = e.api) || void 0 === i ? void 0 : i.resourceTypeHandler(n) : "";
					return "fetch" !== i && "static" !== i && (n = t.getResponseHeader("content-type"), i = 400 <= t.status || "string" != typeof n || !p(n) ? "fetch": "static"),
					i
				},
				overrideXhr: function(e) {
					var t = this,
					n = {
						name: this.name,
						send: function(n, o) {
							var i = Date.now();
							n.addEventListener("loadend", (function() {
								var r, a, u, c, l = n.aegisUrl;
								if (l) {
									var p = Date.now() - i,
									v = {
										url: l,
										isHttps: d(l),
										status: n.status || 0,
										method: n.aegisMethod || "get",
										type: "fetch",
										duration: p,
										payload: new E(n)
									};
									if ("fetch" === t.getRequestType(e, n, l)) try {
										var y, w, b, O, R, S = null === (r = e.api) || void 0 === r ? void 0 : r.apiDetail,
										T = "req url: " + l + " \n                                \nreq method: " + v.method + " \n                                \nreq param: " + (S ? g(o) : "") + " \n                                \nres duration: " + p + " \n                                \nres status: " + v.status + " \n                                \nres data: " + (S ? g(n.response) : "");
										t.publishNormalLog({
											msg: T,
											level: m.API_RESPONSE,
											trace: n.aegisTjgTrace
										}),
										("function" == typeof(null === (a = e.api) || void 0 === a ? void 0 : a.retCodeHandler) ? (R = (y = e.api.retCodeHandler(n.response, l, n) || {}).code, w = y.isErr, v.ret = R, w) : (b = h(n.response, e.api), v.ret = b, O = (O = null === (u = e.api) || void 0 === u ? void 0 : u.errCode) && [].concat(O), R = (R = null === (c = e.api) || void 0 === c ? void 0 : c.code) && [].concat(R), O && -1 !== O.indexOf(b) || R && -1 === R.indexOf(b) || !O && !R && "0" !== b && "unknown" !== b)) && t.publishNormalLog({
											msg: T,
											level: m.RET_ERROR
										})
									} catch(r) {
										v.ret = "unknown"
									} else Object.assign(v, {
										type: "static",
										urlQuery: f(l, !0),
										domainLookup: s.number,
										connectTime: s.number
									});
									t.publishSpeed(v)
								}
							}))
						}
					};
					this.hackXHROptions = n,
					I(this.hackXHROptions)
				},
				publishSpeed: function(e) {
					var t = this;
					e.url = f(e.url),
					this.$walk((function(n) {
						var o = t.$getConfig(n);
						"fetch" === e.type && o && "function" == typeof o.urlHandler ? n.speedLogPipeline(i(i({},
						e), {
							url: encodeURIComponent(o.urlHandler(e.url, e.payload))
						})) : n.speedLogPipeline(e)
					}))
				},
				publishNormalLog: function(e) {
					this.$walk((function(t) {
						t.normalLogPipeline(e)
					}))
				},
				destroy: function() {
					var e, t;
					this.option.publishSpeed = function() {},
					this.option.publishNormalLog = function() {},
					this.option.hackXHROptions && A(this.option.hackXHROptions),
					this.option.hackFetchOptions && (e = this.option.hackFetchOptions, -1 !== (t = G.findIndex((function(t) {
						return t.name === e.name
					}))) && G.splice(t, 1))
				}
			}));
			function Q(e) {
				this.aegisPlugin = !0,
				this.name = "",
				this.instances = [],
				this.inited = !1,
				e.$walk = this.walk.bind(this),
				e.$getConfig = this.getConfig.bind(this),
				this.option = e,
				this.name = e.name
			}
			function K(e) {
				var t, n, o, i, r, s, u, c, l, f, d, p, h, g = this;
				this.config = {
					version: 0,
					delay: 1e3,
					onError: !0,
					repeat: 5,
					random: 1,
					aid: !0,
					device: !0,
					pagePerformance: !0,
					webVitals: !0,
					speedSample: !0,
					hostUrl: "https://aegis.qq.com",
					url: "",
					offlineUrl: "",
					whiteListUrl: "",
					pvUrl: "",
					speedUrl: "",
					customTimeUrl: "",
					performanceUrl: "",
					webVitalsUrl: "",
					eventUrl: ""
				},
				this.isWhiteList = !1,
				this.lifeCycle = new a,
				this.bean = {},
				this.normalLogPipeline = x([T(this, 5), L,
				function(e, n) {
					if ("number" != typeof t.config.random && (console.warn("random must in [0, 1], default is 1."), t.config.random = 1), Math.random() < t.config.random) return n(e)
				},
				(p = (t = this).config, h = {},
				function(e, t) {
					var n = "number" == typeof p.repeat ? p.repeat: 5;
					if (0 === n) return t(e);
					t(e.filter((function(e) {
						return e.level !== m.ERROR && e.level !== m.PROMISE_ERROR && e.level !== m.AJAX_ERROR && e.level !== m.SCRIPT_ERROR && e.level !== m.IMAGE_ERROR && e.level !== m.CSS_ERROR && e.level !== m.MEDIA_ERROR || (h[e.msg] = h[e.msg] || 0, h[e.msg] += 1, !(h[e.msg] > n))
					})))
				}), (f = this.lifeCycle.emit, d = this.config,
				function(e, t) {
					var n = d.logCreated;
					return "function" != typeof n ? (f("beforeWrite", e), t(e)) : (e = e.filter((function(e) {
						return ! 1 !== n(e)
					})), f("beforeWrite", e), t(e))
				}), (l = this, setTimeout((function() {
					var e; (e = void 0 === (e = l.config.pvUrl) ? "": e) && l.send({
						url: e,
						type: v.PV
					},
					(function() {}), (function(e) {
						"403 forbidden" === e && l.destroy()
					}))
				}), 100),
				function(e, t) {
					t(e)
				}), (u = s = r = !1, c = [], (o = this).lifeCycle.on("onConfigChange", (function() {
					i && clearTimeout(i),
					i = setTimeout((function() {
						var e; ! u && o.config && (u = !0, (e = void 0 === (e = o.config.whiteListUrl) ? "": e) && o.send({
							url: e,
							type: v.WHITE_LIST
						},
						(function(e) {
							s = !0;
							try {
								var t = e.data || JSON.parse(e),
								n = t.retcode,
								i = t.result,
								a = void 0 === i ? {}: i;
								if (0 === n) {
									if (r = a.is_in_white_list, o.isWhiteList = r, a.shutdown) return void o.destroy();
									0 <= a.rate && a.rate <= 1 && (o.config.random = a.rate)
								}
								o.isWhiteList && c.length ? U(o)(c.splice(0), (function() {})) : !o.isWhiteList && c.length && (c.length = 0);
								var u = o.config.onWhitelist;
								"function" == typeof u && u(r)
							} catch(e) {}
						}), (function(e) {
							"403 forbidden" === e && o.destroy(),
							s = !0
						})), u = !1)
					}), o.config.uin ? 50 : 500)
				})), o.lifeCycle.on("destroy", (function() {
					c.length = 0
				})),
				function(e, t) {
					r ? t(e.concat(c.splice(0)).map((function(e) {
						return j(e),
						e
					}))) : (e = e.filter((function(e) {
						return e.level !== m.INFO && e.level !== m.API_RESPONSE ? (j(e), !0) : (s || (c.push(e), 200 <= c.length && (c.length = 200)), !1)
					}))).length && t(e)
				}),
				function(e, t) {
					var n = JSON.parse(JSON.stringify(e));
					g.lifeCycle.emit("beforeReport", n);
					var o = g.config.beforeReport; (e = "function" == typeof o ? e.filter((function(e) {
						return ! 1 !== o(e)
					})) : e).length && t(e)
				},
				U(this)]),
				this.eventPipeline = x([T(this, 5),
				function(e) {
					var t = e.map((function(e) {
						return {
							name: e.name,
							ext1: e.ext1 || g.config.ext1 || "",
							ext2: e.ext2 || g.config.ext2 || "",
							ext3: e.ext3 || g.config.ext3 || ""
						}
					}));
					return g.send({
						url: g.config.eventUrl + "?payload=" + encodeURIComponent(JSON.stringify(t)),
						type: v.EVENT,
						log: e
					},
					(function() {}), (function(e) {
						"403 forbidden" === e && g.destroy()
					}))
				}]),
				this.timeMap = {},
				this.customTimePipeline = x([T(this, 5),
				function(e) {
					return g.send({
						url: g.config.customTimeUrl + "?payload=" + encodeURIComponent(JSON.stringify({
							custom: e
						})),
						type: v.CUSTOM,
						log: e
					},
					(function() {}), (function(e) {
						"403 forbidden" === e && g.destroy()
					}))
				}]),
				this.config = (n = this.config, void 0 === (e = e.hostUrl) && (e = "https://aegis.qq.com"), n.url = n.url || e + "/collect", n.offlineUrl = n.offlineUrl || e + "/offline", n.whiteListUrl = n.whiteListUrl || e + "/collect/whitelist", n.pvUrl = n.pvUrl || e + "/collect/pv", n.eventUrl = n.eventUrl || e + "/collect/events", n.speedUrl = n.speedUrl || e + "/speed", n.customTimeUrl = n.customTimeUrl || e + "/speed/custom", n.performanceUrl = n.performanceUrl || e + "/speed/performance", n.webVitalsUrl = n.webVitalsUrl || e + "/speed/webvitals", n),
				K.instances.push(this)
			} (xe = N = N || {})[xe.unknown = 100] = "unknown",
			xe[xe.wifi = 1] = "wifi",
			xe[xe.net2g = 2] = "net2g",
			xe[xe.net3g = 3] = "net3g",
			xe[xe.net4g = 4] = "net4g",
			xe[xe.net5g = 5] = "net5g",
			xe[xe.net6g = 6] = "net6g",
			(Ae = k = k || {})[Ae.android = 1] = "android",
			Ae[Ae.ios = 2] = "ios",
			Ae[Ae.windows = 3] = "windows",
			Ae[Ae.macos = 4] = "macos",
			Ae[Ae.linux = 5] = "linux",
			Ae[Ae.other = 100] = "other";
			var Y, z, Z, ee, te, ne, oe, ie, re, se = new D({
				name: "device",
				onNewAegis: function(e) {
					e.extendBean("platform", this.getPlatform()),
					e.extendBean("netType", N.unknown),
					this.refreshNetworkTypeToBean(e)
				},
				getPlatform: function() {
					var e = {
						android: /\bAndroid\s*([^;]+)/,
						ios: /\b(iPad|iPhone|iPod)\b.*? OS ([\d_]+)/,
						windows: /\b(Windows NT)/,
						macos: /\b(Mac OS)/,
						linux: /\b(Linux)/i
					},
					t = Object.keys(e).find((function(t) {
						return e[t].test(navigator.userAgent)
					}));
					return t ? k[t] : k.other
				},
				refreshNetworkTypeToBean: function(e) {
					var t = this,
					n = e.config;
					n && ("function" == typeof n.getNetworkType ? n.getNetworkType: ae)((function(n) {
						N[n] || (n = N.unknown),
						e.extendBean("netType", n),
						t.NetworkRefreshTimer = setTimeout((function() {
							t.refreshNetworkTypeToBean(e),
							clearTimeout(t.NetworkRefreshTimer)
						}), 1e4)
					}))
				}
			}),
			ae = function(e) {
				var t = "",
				n = navigator.userAgent.match(/NetType\/(\w+)/);
				n ? t = n[1] : navigator.connection && (t = navigator.connection.effectiveType || navigator.connection.type),
				e((t = t = t || "unknown", 0 <= (t = String(t).toLowerCase()).indexOf("4g") ? N.net4g: 0 <= t.indexOf("wifi") ? N.wifi: 0 <= t.indexOf("5g") ? N.net5g: 0 <= t.indexOf("6g") ? N.net6g: 0 <= t.indexOf("3g") ? N.net3g: 0 <= t.indexOf("2g") ? N.net2g: N.unknown))
			},
			ue = new D({
				name: "onError"
			}),
			ce = ue = new D({
				name: "onError",
				onNewAegis: function(e) {
					this.startListen(e)
				},
				startListen: function(e) {
					var t = this,
					n = window.onerror;
					function o(e) {
						e = e && b(e.reason),
						t.publishErrorLog({
							msg: "PROMISE_ERROR: " + e,
							level: m.PROMISE_ERROR
						})
					}
					function i(e) {
						if (e = (null == e ? void 0 : e.target) || (null == e ? void 0 : e.srcElement)) {
							var n = e.src || e.href;
							if (e = e.tagName, "string" == typeof n && e) {
								var o = {
									msg: e + " load fail: " + n,
									level: m.INFO
								};
								switch (e.toLowerCase()) {
								case "script":
									o.level = m.SCRIPT_ERROR;
									break;
								case "link":
									o.level = m.CSS_ERROR;
									break;
								case "img":
									if ( - 1 < window.location.href.indexOf(n)) return;
									o.level = m.IMAGE_ERROR;
									break;
								case "audio":
								case "video":
									o.level = m.MEDIA_ERROR;
									break;
								default:
									return
								}
								t.publishErrorLog(o)
							}
						}
					}
					window.onerror = function() {
						for (var e = [], o = 0; o < arguments.length; o++) e[o] = arguments[o];
						t.publishErrorLog({
							msg: (b(e[0]) || "") + " @ (" + (b(e[1]) || "") + ":" + (e[2] || 0) + ":" + (e[3] || 0) + ")\n          \n" + b(e[4] || ""),
							level: m.ERROR
						}),
						null == n || n.call.apply(n,
						function() {
							for (var e = 0,
							t = 0,
							n = arguments.length; t < n; t++) e += arguments[t].length;
							var o = Array(e),
							i = 0;
							for (t = 0; t < n; t++) for (var r = arguments[t], s = 0, a = r.length; s < a; s++, i++) o[i] = r[s];
							return o
						} ([window], e))
					},
					window.addEventListener("unhandledrejection", o),
					window.document.addEventListener("error", i, !0),
					e.lifeCycle.on("destroy", (function() {
						0 === ue.countInstance() && (window.document.removeEventListener("unhandledrejection", o), window.document.removeEventListener("error", i, !0))
					}));
					var r = this,
					s = {
						name: this.name,
						send: function(t, n) {
							t.addEventListener("loadend", (function() {
								var o, i = "";
								this.aegisTimeout ? i = "timeout": this.status ? 400 <= this.status && (i = "error") : i = "failed",
								e.config.hostUrl && this.aegisUrl && -1 < (null === (o = this.aegisUrl) || void 0 === o ? void 0 : o.indexOf(e.config.hostUrl)) || (o = null === (o = e.config.api) || void 0 === o ? void 0 : o.apiDetail, i && r.publishErrorLog({
									msg: "AJAX_ERROR: request " + i + "\n                      \nres status: " + (this.status || 0) + "\n                      \nres duration: " + (Date.now() - t.aegisXhrStartTime) + "\n                      \nreq url: " + this.aegisUrl + "\n                      \nreq method: " + this.aegisMethod + "\n                      \nreq param: " + (o ? g(n) : "") + "\n                      \nres data: " + (o ? null === (o = (o = t).responseType && "text" !== o.responseType ? "": o.responseText) || void 0 === o ? void 0 : o.slice(0, 1e3) : ""),
									level: m.AJAX_ERROR
								}))
							})),
							t.addEventListener("timeout", (function() {
								t.aegisTimeout = !0
							}))
						}
					};
					this.hackXHROptions = s,
					I(this.hackXHROptions)
				},
				publishErrorLog: function(e) {
					this.$walk((function(t) {
						t.normalLogPipeline(e)
					}))
				},
				destroy: function() {
					this.option.publishErrorLog = function() {},
					this.option.hackXHROptions && A(this.option.hackXHROptions)
				}
			}),
			le = (new D({
				name: "pagePerformance"
			}), 1),
			fe = new D({
				name: "pagePerformance",
				onNewAegis: function(e) {
					var t = this;
					if (C()) if (Y) this.publish(Y, e);
					else try {
						this.getFirstScreenTiming(e, (function(n) {
							var o, i = performance.timing;
							i && (o = i.loadEventStart - i.domInteractive, Y = {
								dnsLookup: i.domainLookupEnd - i.domainLookupStart,
								tcp: i.connectEnd - i.connectStart,
								ssl: 0 === i.secureConnectionStart ? 0 : i.requestStart - i.secureConnectionStart,
								ttfb: i.responseStart - i.requestStart,
								contentDownload: i.responseEnd - i.responseStart,
								domParse: i.domInteractive - i.domLoading,
								resourceDownload: o = o < 0 ? 1070 : o,
								firstScreenTiming: Math.floor(n)
							},
							t.publish(Y, e))
						}))
					} catch(e) {}
				},
				publish: function(e, t) {
					var n, o = [];
					for (n in e) o.push(n + "=" + e[n]);
					var i = this.$getConfig(t);
					if (i) {
						var r = -1 === t.config.performanceUrl.indexOf("?") ? "?": "&";
						return "function" == typeof i.urlHandler ? t.send({
							url: t.config.performanceUrl + r + o.join("&") + "&from=" + (encodeURIComponent(i.urlHandler()) || window.location.href),
							beanFilter: ["from"],
							type: v.PERFORMANCE,
							log: e
						}) : t.send({
							url: t.config.performanceUrl + r + o.join("&"),
							type: v.PERFORMANCE,
							log: e
						})
					}
				},
				getFirstScreenTiming: function(e, t) {
					var n = ["script", "style", "link", "br"],
					o = [],
					i = this,
					r = {},
					s = new MutationObserver((function(e) {
						var t = {
							roots: [],
							rootsDomNum: [],
							time: performance.now()
						};
						e.forEach((function(e) {
							e && e.addedNodes && e.addedNodes.forEach && e.addedNodes.forEach((function(e) {
								1 === e.nodeType && (e.hasAttribute("AEGIS-FIRST-SCREEN-TIMING") || e.querySelector("[AEGIS-FIRST-SCREEN-TIMING]")) ? (Object.prototype.hasOwnProperty.apply(r, [t.time]) || (r[t.time] = []), r[t.time].push(e)) : 1 !== e.nodeType || -1 !== n.indexOf(e.nodeName.toLocaleLowerCase()) || i.isEleInArray(e, t.roots) || e.hasAttribute("AEGIS-IGNORE-FIRST-SCREEN-TIMING") || (t.roots.push(e), t.rootsDomNum.push(i.walkAndCount(e) || 0))
							}))
						})),
						t.roots.length && o.push(t)
					}));
					s.observe(document, {
						childList: !0,
						subtree: !0
					});
					var a = function() {
						var n = 0,
						u = 0,
						c = Object.keys(r).filter((function(e) {
							return r[ + e].find((function(e) {
								return i.isInFirstScreen(e)
							}))
						}));
						c.length ? (u = Math.max.apply(null, c), e.firstScreenInfo = {
							element: null === (c = r[u]) || void 0 === c ? void 0 : c[0],
							timing: u,
							markDoms: r
						}) : o.forEach((function(t) {
							for (var o = 0; o < t.roots.length; o++) t.rootsDomNum[o] > n && i.isInFirstScreen(t.roots[o]) && (n = t.rootsDomNum[o], u = t.time, e.firstScreenInfo = {
								element: t.roots[o],
								timing: u
							})
						})),
						!u && le ? setTimeout((function() {
							return a()
						}), 3e3) : (s.disconnect(), null == t || t(u)),
						--le
					};
					setTimeout((function() {
						return a()
					}), 3e3)
				},
				isEleInArray: function(e, t) {
					return ! (!e || e === document.documentElement) && ( - 1 !== t.indexOf(e) || this.isEleInArray(e.parentElement, t))
				},
				isInFirstScreen: function(e) {
					if (!e || "function" != typeof e.getBoundingClientRect) return ! 1;
					var t = e.getBoundingClientRect(),
					n = window.innerHeight;
					return e = window.innerWidth,
					0 <= t.left && t.left < e && 0 <= t.top && t.top < n && 0 < t.width && 0 < t.height
				},
				walkAndCount: function(e) {
					var t = 0;
					if (e && 1 === e.nodeType) {
						t += 1;
						var n = e.children;
						if (null != n && n.length) for (var o = 0; o < n.length; o++) t += this.walkAndCount(n[o])
					}
					return t
				}
			});
			function de() {
				te = [],
				Z = -1,
				z = null,
				ge(addEventListener)
			}
			function pe(e, t) {
				z || (z = t, Z = e, ee = new Date, ge(removeEventListener), he())
			}
			function he() {
				var e;
				0 <= Z && Z < ee - oe && (e = {
					entryType: "first-input",
					name: z.type,
					target: z.target,
					cancelable: z.cancelable,
					startTime: z.timeStamp,
					processingStart: z.timeStamp + Z
				},
				te.forEach((function(t) {
					t(e)
				})), te = [])
			}
			function ge(e) { ["mousedown", "keydown", "touchstart", "pointerdown"].forEach((function(t) {
					return e(t, ie, ne)
				}))
			}
			function me(e, t) {
				return {
					name: e,
					value: void 0 === t ? -1 : t,
					delta: 0,
					entries: [],
					id: "v1-".concat(Date.now(), "-").concat(Math.floor(8999999999999 * Math.random()) + 1e12)
				}
			}
			function ve(e, t) {
				try {
					if (PerformanceObserver.supportedEntryTypes.includes(e)) {
						if ("first-input" === e && !("PerformanceEventTiming" in self)) return;
						var n = new PerformanceObserver((function(e) {
							return e.getEntries().map(t)
						}));
						return n.observe({
							type: e,
							buffered: !0
						}),
						n
					}
				} catch(e) {}
			}
			function ye(e) {
				addEventListener("pageshow", (function(t) {
					t.persisted && e(t)
				}), !0)
			}
			function we(e, t, n) {
				var o;
				return function() {
					0 <= t.value && (n || Te.has(t) || "hidden" === document.visibilityState) && (t.delta = t.value - (o || 0), !t.delta && void 0 !== o || (o = t.value, e(t)))
				}
			}
			function be() {
				Se((function(e) {
					e = e.timeStamp,
					Le = e
				}), !0)
			}
			function Ee() {
				return Le < 0 && ((Le = self.webVitals.firstHiddenTime) === 1 / 0 && be(), ye((function() {
					setTimeout((function() {
						Le = "hidden" === document.visibilityState ? 0 : 1 / 0,
						be()
					}), 0)
				}))),
				{
					get timeStamp() {
						return Le
					}
				}
			}
			ne = {
				passive: !0,
				capture: !0
			},
			oe = new Date,
			ie = function(e) {
				var t, n, o, i;
				function r() {
					pe(n, o),
					i()
				}
				function s() {
					i()
				}
				e.cancelable && (t = (1e12 < e.timeStamp ? new Date: performance.now()) - e.timeStamp, "pointerdown" == e.type ? (n = t, o = e, i = function() {
					removeEventListener("pointerup", r, ne),
					removeEventListener("pointercancel", s, ne)
				},
				addEventListener("pointerup", r, ne), addEventListener("pointercancel", s, ne)) : pe(t, e))
			},
			re = "hidden" === document.visibilityState ? 0 : 1 / 0,
			addEventListener("visibilitychange", (function e(t) {
				"hidden" === document.visibilityState && (re = t.timeStamp, removeEventListener("visibilitychange", e, !0))
			}), !0),
			de(),
			self.webVitals = {
				firstInputPolyfill: function(e) {
					te.push(e),
					he()
				},
				resetFirstInputPolyfill: de,
				get firstHiddenTime() {
					return re
				}
			};
			var Oe, Re, Se = function(e, t) {
				function n(o) {
					"pagehide" !== o.type && "hidden" !== document.visibilityState || (e(o), t && (removeEventListener("visibilitychange", n, !0), removeEventListener("pagehide", n, !0)))
				}
				addEventListener("visibilitychange", n, !0),
				addEventListener("pagehide", n, !0)
			},
			Te = new("function" == typeof WeakSet ? WeakSet: Set),
			Le = -1,
			xe = (new D({
				name: "webVitals"
			}), new D({
				name: "webVitals",
				onNewAegis: function(e) {
					if (C() && "function" == typeof window.PerformanceObserver) try {
						v = this.publish.bind(this, e),
						w = Ee(),
						b = me("FCP"),
						E = performance.getEntriesByName("first-contentful-paint")[0],
						O = E ? null: ve("paint", T),
						(E || O) && (y = we(v, b, void 0), E && T(E), ye((function(e) {
							b = me("FCP"),
							y = we(v, b, void 0),
							requestAnimationFrame((function() {
								requestAnimationFrame((function() {
									b.value = performance.now() - e.timeStamp,
									Te.add(b),
									y()
								}))
							}))
						}))),
						f = this.publish.bind(this, e),
						h = Ee(),
						g = me("LCP"),
						(m = ve("largest-contentful-paint", S)) && (d = we(f, g, void 0), p = function() {
							Te.has(g) || (m.takeRecords().map(S), m.disconnect(), Te.add(g), d())
						},
						["keydown", "click"].forEach((function(e) {
							addEventListener(e, p, {
								once: !0,
								capture: !0
							})
						})), Se(p, !0), ye((function(e) {
							g = me("LCP"),
							d = we(f, g, void 0),
							requestAnimationFrame((function() {
								requestAnimationFrame((function() {
									g.value = performance.now() - e.timeStamp,
									Te.add(g),
									d()
								}))
							}))
						}))),
						s = this.publish.bind(this, e),
						a = Ee(),
						u = me("FID"),
						c = ve("first-input", R),
						l = we(s, u, void 0),
						c && Se((function() {
							c.takeRecords().map(R),
							c.disconnect()
						}), !0),
						c || window.webVitals.firstInputPolyfill(R),
						ye((function() {
							u = me("FID"),
							l = we(s, u, void 0),
							window.webVitals.resetFirstInputPolyfill(),
							window.webVitals.firstInputPolyfill(R)
						})),
						n = this.publish.bind(this, e),
						i = me("CLS", 0),
						(r = ve("layout-shift", t)) && (o = we(n, i, void 0), Se((function() {
							r.takeRecords().map(t),
							o()
						})), ye((function() {
							i = me("CLS", 0),
							o = we(n, i, void 0)
						})))
					} catch(e) {}
					function t(e) {
						e.hadRecentInput || (i.value += e.value, i.entries.push(e), o())
					}
					var n, o, i, r, s, a, u, c, l, f, d, p, h, g, m, v, y, w, b, E, O;
					function R(e) {
						e.startTime < a.timeStamp && (u.value = e.processingStart - e.startTime, u.entries.push(e), Te.add(u), l())
					}
					function S(e) {
						var t = e.startTime;
						t < h.timeStamp && (g.value = t, g.entries.push(e)),
						d()
					}
					function T(e) {
						"first-contentful-paint" === e.name && (O && O.disconnect(), e.startTime < w.timeStamp && (b.value = e.startTime, b.entries.push(e), Te.add(b), y()))
					}
				},
				publish: function(e, t) {
					var n, o = {
						FCP: -1,
						LCP: -1,
						FID: -1,
						CLS: -1
					},
					i = t.name,
					r = t.value,
					s = [];
					for (n in o) s.push(n + "=" + (n === i ? r: o[n]));
					var a = -1 === e.config.performanceUrl.indexOf("?") ? "?": "&";
					setTimeout((function() {
						e.send({
							url: e.config.webVitalsUrl + a + s.join("&"),
							type: v.VITALS,
							log: t
						})
					}))
				},
				destroy: function() {
					this.option.publish = function() {}
				}
			})),
			Pe = 0,
			Ce = (o(Re = Ne, Ae = Oe = F), Re.prototype = null === Ae ? Object.create(Ae) : (ke.prototype = Ae.prototype, new ke), Ne.prototype.getBean = function(e) {
				var t = this;
				return void 0 === e && (e = []),
				"" + Object.getOwnPropertyNames(this.bean).filter((function(t) {
					return - 1 === e.indexOf(t)
				})).map((function(e) {
					return e + "=" + t.bean[e]
				})).join("&")
			},
			Ne.prototype.request = function(e, t, n) {
				var o = this;
				if (e && "string" == typeof e.url && "" !== e.url && this.bean.id) if (this.requesting) this.requestQueue.push({
					options: e,
					success: t,
					fail: n
				});
				else {
					this.requesting = !0;
					var i = e.url; ! 1 !== e.addBean && (i = i + ( - 1 === i.indexOf("?") ? "?": "&") + this.getBean(e.beanFilter)),
					e.url = i;
					var r = e.method || "get";
					if (! (e = (i = this.config.onBeforeRequest) ? i(e, this) : e)) return console.log("Sending request blocked");
					if (!e.url) return console.warn("Please handle the parameters reasonably, options.url is necessary");
					var s = new XMLHttpRequest;
					s.sendByAegis = !0,
					s.addEventListener("readystatechange", (function() {
						var e;
						4 === s.readyState && (o.requesting = !1, o.requestQueue && ((e = o.requestQueue.shift()) && o.request(e.options, e.success, e.fail), 400 <= s.status || 0 === s.status ? null == n || n(s.response) : null == t || t(s.response)))
					})),
					"get" === r.toLocaleLowerCase() ? (s.open("get",
					function(e, t) {
						if ("string" != typeof e) return "";
						if ("object" == typeof t && t) {
							var n = Object.getOwnPropertyNames(t).map((function(e) {
								var n = t[e];
								return e + "=" + ("string" == typeof n ? encodeURIComponent(n) : encodeURIComponent(JSON.stringify(n)))
							})).join("&").replace(/eval/gi, "evaI");
							return e + ( - 1 === e.indexOf("?") ? "?": "&") + n
						}
						return e
					} (e.url, e.data)), s.send()) : (s.open("post", e.url), e.contentType && s.setRequestHeader("Content-Type", e.contentType), "string" == typeof e.data && (e.data = e.data.replace(/eval/gi, "evaI")), s.send(e.data))
				}
			},
			Ne.useAsyncPlugin = function(e, t) {
				var n, o = void 0 === (n = (t = void 0 === t ? {}: t).exportsConstructor) ? "aegis-plugin-" + Pe: n,
				i = void 0 === (n = t.onAegisInit) ?
				function() {}: n,
				r = void 0 === (t = t.onAegisInitAndPluginLoaded) ?
				function() {}: t;
				if (Pe += 1, "string" != typeof e) throw new TypeError("useAsyncPlugin first param must be string");
				if ("function" != typeof i || "function" != typeof r) throw new TypeError("onAegisInit and onAegisInitAndPluginLoaded must be function");
				this.use(new D({
					name: "asyncPlugin",
					onNewAegis: function(t) {
						try {
							i(t),
							Ne.asyncPlugin[e] ? r(t, window[Ne.asyncPlugin[e]]) : (n = e, s = o, a = function(n) {
								n || (Ne.asyncPlugin[e] = o, n = window[o], r(t, n))
							},
							u = document.createElement("script"), c = document.head, "function" == typeof s && (a = s, s = ""), u.src = n, u.setAttribute("name", s), u.name = s, u.setAttribute("crossorigin", "anonymous"), u.crossorigin = "anonymous", u.defer = !0, u.hasLoaded = !1, u.onreadystatechange = function() {
								u.hasLoaded || u.readyState && "loaded" !== u.readyState && "complete" !== u.readyState || (u.hasLoaded = !0, "function" == typeof a && a(!1), setTimeout((function() {
									c.contains(u) && c.removeChild(u)
								})))
							},
							u.onload = u.onreadystatechange, u.onerror = function() {
								"function" == typeof a && a(!0),
								setTimeout((function() {
									c.contains(u) && c.removeChild(u)
								}))
							},
							"complete" === document.readyState ? c.appendChild(u) : window.addEventListener("load", (function() {
								c.appendChild(u)
							})))
						} catch(n) {
							console.log("error on below is caused by " + e + " "),
							console.error(n)
						}
						var n, s, a, u, c
					}
				}))
			}, Ne.prototype.uploadLogs = function(e, t) {
				this.lifeCycle.emit("uploadLogs", e = void 0 === e ? {}: e, t = void 0 === t ? {}: t)
			},
			Ne.__version__ = "1.34.64", Ne.sessionID = "session-" + Date.now(), Ne.asyncPlugin = {},
			Ne.urls = {
				aegisCollect: "https://aegis.qq.com/collect",
				flog: "https://cdn-go.cn/vasdev/web_webpersistance_v2/v1.8.2/flog.core.min.js",
				shadowLog: ""
			},
			Ne),
			Ie = new D({
				name: "offlineLog"
			}),
			Ae = (F = Ie = new D({
				name: "offlineLog",
				onNewAegis: function(e) {
					var t = Ce.urls.flog,
					n = e.config,
					o = [],
					r = null,
					s = null,
					a = function(e) {
						s = e
					},
					c = function(e) {
						o = o.concat(e)
					},
					l = function(e, t) {
						r = {
							conds: e = void 0 === e ? {}: e,
							params: t = void 0 === t ? {}: t
						}
					};
					Ce.useAsyncPlugin(t, {
						exportsConstructor: "Flog",
						onAegisInit: function(e) {
							e.lifeCycle.on("beforeWrite", c),
							e.lifeCycle.on("uploadLogs", l),
							e.lifeCycle.on("onConfigChange", a)
						},
						onAegisInitAndPluginLoaded: function(e, t) {
							var f, d, p, h, g, y = void 0 === (R = n.dbConfig) ? {}: R,
							w = void 0 === (S = n.url) ? Ce.urls.aegisCollect: S,
							E = n.offlineLogExp,
							O = void 0 === E ? 3 : E,
							R = n.id,
							S = n.uin;
							null === (E = e.lifeCycle) || void 0 === E || E.remove("beforeWrite", c),
							null === (E = e.lifeCycle) || void 0 === E || E.remove("uploadLogs", l),
							null === (E = e.lifeCycle) || void 0 === E || E.remove("onConfigChange", a),
							y = Object.assign({
								lookupUrl: w + "/offlineAuto?id=" + R,
								preservedDay: O,
								id: R,
								uin: S,
								aid: (null === (S = e.bean) || void 0 === S ? void 0 : S.aid) || "",
								beforeRequest: e.config.beforeRequest,
								afterRequest: e.config.afterRequest
							},
							y, {
								sessionId: Ce.sessionID
							});
							try {
								var T = new t(y);
								o.forEach((function(e) {
									T.add(i(i({},
									e), {
										level: e.level === m.INFO_ALL ? m.INFO: e.level
									}))
								})),
								null === (f = e.lifeCycle) || void 0 === f || f.on("beforeWrite", (function(e) { (e = void 0 === e ? [] : e).forEach((function(e) {
										T.add(i(i({},
										e), {
											level: e.level === m.INFO_ALL ? m.INFO: e.level
										}))
									}))
								})),
								null === (d = e.lifeCycle) || void 0 === d || d.on("uploadLogs", (function(t, o) {
									var i;
									void 0 === t && (t = {}),
									void 0 === o && (o = {}),
									T.uploadLogs(Object.assign({
										id: n.id,
										uin: n.uin,
										aid: null === (i = e.bean) || void 0 === i ? void 0 : i.aid
									},
									t), o)
								})),
								null === (p = e.lifeCycle) || void 0 === p || p.on("onConfigChange", (function(e) {
									T.setConfig(e)
								})),
								null === (h = e.lifeCycle) || void 0 === h || h.on("destroy", (function() {
									0 === Ie.countInstance() && (o.length = 0, "function" == typeof((r = null) == T ? void 0 : T.destroy) && T.destroy())
								})),
								T.on("PERREVENT", (function(t) {
									e.send({
										type: v.OFFLINE,
										data: u({
											msg: b(t),
											level: m.INFO
										}),
										contentType: "application/x-www-form-urlencoded",
										method: "post",
										addBean: !1,
										url: n.url + "?id=893&sessionId=" + Ce.sessionID + "&uin=" + n.uin + "&from=" + n.id + "&count=1&version=1.34.64",
										log: v.OFFLINE
									})
								})),
								s && (T.setConfig(s), s = null),
								r && (null === (g = e.lifeCycle) || void 0 === g || g.emit("uploadLogs", r.conds, r.params), r = null),
								e.flog = T
							} catch(e) {
								console.log(e)
							}
						}
					})
				}
			}), new D({
				name: "spa"
			}), new D({
				name: "spa",
				init: function() {
					history.pushState = this.wr("pushState") || history.pushState,
					history.replaceState = this.wr("replaceState") || history.replaceState;
					var e = (null === (e = location.href) || void 0 === e ? void 0 : e.split("?"))[0];
					this.$fireUrl = e,
					this.sendPv = this.sendPv.bind(this),
					this.onPageChange()
				},
				onPageChange: function() {
					window.addEventListener("replaceState", this.sendPv),
					window.addEventListener("pushState", this.sendPv),
					window.addEventListener("popstate", this.sendPv)
				},
				wr: function(e) {
					var t = history[e];
					return "function" == typeof t && !history.__hasWrittenByTamSpa && (Object.defineProperty(history, "__hasWrittenByTamSpa", {
						value: !0,
						enumerable: !1
					}),
					function() {
						var n = t.apply(this, arguments),
						o = null;
						return "function" == typeof Event ? o = new Event(e) : (o = document.createEvent("HTMLEvents")).initEvent(e, !1, !0),
						window.dispatchEvent(o),
						n
					})
				},
				sendPv: function() {
					var e = this;
					setTimeout((function() {
						var t = location.href,
						n = (null === (n = location.href) || void 0 === n ? void 0 : n.split("?"))[0];
						n && n !== e.$fireUrl && (e.$walk((function(e) {
							e.send({
								url: e.config.pvUrl + "?from=" + encodeURIComponent(t),
								beanFilter: ["from"],
								type: v.WHITE_LIST
							})
						})), e.$fireUrl = n)
					}), 0)
				},
				destroy: function() {
					window.removeEventListener("replaceState", this.option.sendPv),
					window.removeEventListener("pushState", this.option.sendPv),
					window.removeEventListener("popstate", this.option.sendPv)
				}
			}));
			function Ne(e) {
				var t, n, o, r, s, a = Oe.call(this, e) || this;
				a.requestQueue = [],
				a.requesting = !1,
				a.speedLogPipeline = x([(r = a.config, s = {},
				function(e, t) {
					var n, o;
					r.speedSample ? (o = "object" == typeof r.repeat ? r.repeat: {
						repeat: r.repeat
					},
					n = +o.speed || +o.repeat || 5, Array.isArray(e) ? (o = e.filter((function(e) {
						var t = !s[e.url] || s[e.url] < n;
						return s[e.url] = 1 + ~~s[e.url],
						t
					}))).length && t(o) : (!s[e.url] || s[e.url] < n) && (s[e.url] = 1 + ~~s[e.url], t(e))) : t(e)
				}), T(a), (o = a,
				function(e, t) {
					ae((function(n) {
						o.extendBean("netType", n),
						t(e)
					}))
				}),
				function(e, t) {
					a.lifeCycle.emit("beforeReportSpeed", e);
					var n = a.config.beforeReportSpeed;
					if ((e = "function" == typeof n ? e.filter((function(e) {
						var t = !1 !== n(e);
						return "fetch" === e.type && void 0 === e.ret && e.payload && (e.ret = h(e.payload.data.response, a.config.api)),
						t
					})) : e).length) return t(e)
				},
				function(e) {
					return a.send({
						type: v.SPEED,
						url: "" + a.config.speedUrl,
						method: "post",
						data: (t = e, n = a.bean, r = {
							fetch: [],
							static: []
						},
						s = new FormData, Array.isArray(t) ? t.forEach((function(e) {
							var t = H(e);
							r[e.type].push(t)
						})) : (o = H(t), r[t.type].push(o)), s.append("payload", JSON.stringify(i({
							duration: r
						},
						n))), s),
						log: e
					});
					var t, n, o, r, s
				}]), e.asyncPlugin = !0;
				try {
					"undefined" != typeof document && (e.uin = e.uin || (null !== (t = document.cookie.match(/\buin=\D+(\d*)/)) && void 0 !== t ? t: [])[1] || (null !== (n = document.cookie.match(/\bilive_uin=\D*(\d+)/)) && void 0 !== n ? n: [])[1] || ""),
					a.init(e),
					a.extendBean("sessionId", Ne.sessionID),
					a.extendBean("from", encodeURIComponent(e.pageUrl || location.href)),
					"undefined" != typeof document && a.extendBean("referer", encodeURIComponent(document.referrer || "")),
					e.ext1 && a.extendBean("ext1", e.ext1),
					e.ext2 && a.extendBean("ext2", e.ext2),
					e.ext3 && a.extendBean("ext3", e.ext3)
				} catch(e) {
					console.warn(e),
					console.log("%cThe above error occurred in the process of initializing Aegis, which will affect your normal use of Aegis.\nIt is recommended that you contact us for feedback and thank you for your support.", "color: red"),
					a.sendSDKError(e)
				}
				return a
			}
			function ke() {
				this.constructor = Re
			}
			return new D({
				name: "ie"
			}),
			Ce.use(ce),
			Ce.use($),
			Ce.use(X),
			Ce.use(fe),
			Ce.use(xe),
			Ce.use(M),
			Ce.use(se),
			Ce.use(F),
			Ce.use(Ae),
			Ce
		} ()
	},
	function(e, t, n) {
		n.r(t),
		n.d(t, "AegisReport", (function() {
			return a
		}));
		var o = n(0),
		i = n.n(o);
		function r(e) {
			if (window.performance && window.performance.timing) for (const t in window.performance.timing) if ("navigationStart" !== t) {
				let n = window.performance.timing[t] - window.performance.timing.navigationStart;
				Number.isNaN(n) || (n < 0 && (n = 0), e.reportTime(t, n))
			}
		}
		function s() {
			return ! window.MSStream && /iPad|iPhone|iPod/.test(navigator.userAgent)
		}
		function a(e) {
			const t = {
				reportApiSpeed: !0,
				reportAssetSpeed: !0,
				beforeReport: e = >(e && e.level >= 4 && e.msg && ((new Image).src = "https://report.qqweb.qq.com/report/error?attachinfo=" + encodeURIComponent(e.msg), u(JSON.stringify(e))), !0)
			};
			"number" == typeof e ? t.id = e: "string" != typeof e || Number.isNaN(Number(e)) ? "string" == typeof e ? t.id = e: "object" == typeof e && Object.assign(t, e) : t.id = Number(e);
			const n = new i.a(t);
			let o = null;
			return window.addEventListener("load", () = >{
				clearTimeout(o),
				setTimeout(() = >{
					r(n)
				},
				1e3)
			}),
			o = setTimeout(() = >{
				r(n)
			},
			3e3),
			n
		}
		function u(e) {
			const t = {
				id: "webviewDebugLog_qqweb",
				subid: location.href,
				content: "info|" + e,
				isall: !0,
				level: "info"
			},
			n = document.createElement("iframe");
			n.style.display = "none",
			n.style.width = 0,
			n.style.height = 0,
			s() && (n.src = "jsbridge://qw_debug/detailLog?p=" + encodeURIComponent(JSON.stringify(t)));
			try {
				window.document && (document.body || document.documentElement) && (document.body || document.documentElement).appendChild && (document.body || document.documentElement).appendChild(n)
			} catch(e) {}
			s() || (n.src = "jsbridge://qw_debug/detailLog?p=" + encodeURIComponent(JSON.stringify(t))),
			setTimeout((function() {
				n && n.parentNode && n.parentNode.removeChild(n)
			}), 500)
		}
		try {
			u("cookie: " + document.cookie)
		} catch(e) {}
		t.
	default = a
	}])
}));